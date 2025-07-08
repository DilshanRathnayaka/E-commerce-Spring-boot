package com.dtech.Ecommerce.auth.authService;


import com.dtech.Ecommerce.auth.authModel.*;
import com.dtech.Ecommerce.auth.authRepo.ForgotPasswordRepo;
import com.dtech.Ecommerce.auth.authRepo.PasswordHistoryRepo;
import com.dtech.Ecommerce.auth.authRepo.TokenRepository;
import com.dtech.Ecommerce.auth.authRepo.UserRepo;
import com.dtech.Ecommerce.auth.authService.Impl.UserImpl;
import com.dtech.Ecommerce.auth.dto.MailBody;
import com.dtech.Ecommerce.auth.dto.UserDTO;
import com.dtech.Ecommerce.auth.utill.RequestType;
import com.dtech.Ecommerce.customer.customerDTO.CustomerDTO;
import com.dtech.Ecommerce.customer.customerService.Impl.CustomerServiceImpl;
import com.dtech.Ecommerce.exeption.CustomExeption;
import com.dtech.Ecommerce.auth.mapper.UserMapper;
import com.dtech.Ecommerce.auth.utill.ChangePassword;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserImpl {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final EmailService emailService;
    private final ForgotPasswordRepo forgotPasswordRepo;
    private final PasswordHistoryRepo passwordHistoryRepo;
    private final CustomerServiceImpl customerService;

    public void register(UserDTO userDTO) {
        CustomerDTO customerDTO = new CustomerDTO();
        User user = userRepo.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new CustomExeption("User already exists");
        }
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        UserDTO userDto = userMapper.toUserDTO(userRepo.saveAndFlush(userMapper.toUser(userDTO)));
        if (userDTO.getType() != null) {
            if(userDTO.getType().matches(RequestType.CUSTOMER.toString())){
                customerDTO.setUser(userDto);
                customerDTO.setEmail(userDTO.getUsername());
                customerService.saveCustomer(customerDTO);
            }
        }
    }


    @Override
    public void login(UserDTO userDTO, HttpServletResponse response) {
        try {
            String rawPassword = userDTO.getPassword();
            List<PasswordHistory> passwordHistories = passwordHistoryRepo.findByUsername(userDTO.getUsername());

            for (PasswordHistory passwordHistory : passwordHistories) {
                if (bCryptPasswordEncoder.matches(rawPassword, passwordHistory.getPassword())) {
                    long daysAgo = ChronoUnit.DAYS.between(passwordHistory.getCreatedDate().toInstant(), Instant.now());
                    if (daysAgo == 0) {
                        long hoursAgo = ChronoUnit.HOURS.between(passwordHistory.getCreatedDate().toInstant(), Instant.now());
                        long minutesAgo = ChronoUnit.MINUTES.between(passwordHistory.getCreatedDate().toInstant(), Instant.now()) % 60;
                        throw new CustomExeption("Password was already used " + hoursAgo + " hours and " + minutesAgo + " minutes ago");
                    } else {
                        throw new CustomExeption("Password was already used " + daysAgo + " days ago");
                    }
                }
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
                String token = jwtService.generateToken(userDTO.getUsername(), userPrinciple.getAuthorities());

                Cookie cookie = new Cookie("jwtE", token);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cookie);
            } else {
                throw new CustomExeption("Invalid username or password");
            }
        } catch (CustomExeption e) {
            throw new CustomExeption(e.getMessage());
        }
    }


    public String logout(String response) {
        try{
            Token tokenEntity = tokenRepository.findByToken(response);
            if (tokenEntity != null) {
                tokenEntity.setValid(false);
                tokenRepository.save(tokenEntity);
            }
        }catch (Exception e){
            throw new CustomExeption("Invalid token");
        }
        return "Logout Successfully";
    }

    @Override
    public String forgotPassword(String email) {
        try{
            User user = userRepo.findByUsername(email);
            int otp = otpGenerator();

            if(user != null) {
                ForgotPassword forgotPassword1 = forgotPasswordRepo.findByStatusAndUser_Username("OTP Sent",email);
                ForgotPassword forgotPassword2 = forgotPasswordRepo.findByStatusAndUser_Username("OTP Verified",email);
                if (forgotPassword1 != null) {
                    if(forgotPassword1.getExpireTime().before(Date.from(Instant.now()))){
                        forgotPassword1.setStatus("OTP Expired");
                        forgotPasswordRepo.save(forgotPassword1);
                    }else{
                        long minutesRemaining = ChronoUnit.MINUTES.between(Instant.now(), forgotPassword1.getExpireTime().toInstant());
                        throw new CustomExeption("OTP already sent to your email. Try again after " + minutesRemaining + " minutes.");
                    }
                }
                if(forgotPassword2 != null){
                    forgotPassword2.setStatus("OTP Expired");
                    forgotPasswordRepo.save(forgotPassword2);
                }
                MailBody mailBody = MailBody.builder()
                        .to(email)
                        .subject("OTP For Forgot Password is")
                        .text("This is the OTP for your Forgot Password Request = " + otp)
                        .build();

                ForgotPassword forgotPassword =  ForgotPassword.builder()
                        .otp(otp)
                        .user(user)
                        .expireTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                        .isVerified(false)
                        .status("OTP Sent")
                        .build();

                emailService.sendSimpleMessage(mailBody);
                forgotPasswordRepo.save(forgotPassword);

            }else {
                throw new UsernameNotFoundException("User not found");
            }
            return "OTP Sent to your email";
        }catch (UsernameNotFoundException |CustomExeption e){
            throw new CustomExeption(e.getMessage());
        }
    }

    @Override
    public String verifyOtp(Integer otp, String email) {
        try{
            User user = userRepo.findByUsername(email);
            if (user != null) {
                ForgotPassword forgotPassword = forgotPasswordRepo.findByOtpAndUserAndStatus(otp,user,"OTP Sent");
                if(forgotPassword == null){
                    throw new CustomExeption("Invalid OTP");
                }
                if (forgotPassword.getExpireTime().before(Date.from(Instant.now()))) {
                    forgotPassword.setIsVerified(false);
                    forgotPassword.setStatus("OTP Expired");
                    forgotPasswordRepo.save(forgotPassword);
                    throw new CustomExeption("OTP Expired");
                }else{
                    forgotPassword.setIsVerified(true);
                    forgotPassword.setStatus("OTP Verified");
                    forgotPasswordRepo.save(forgotPassword);
                    return "OTP Verified";
                }
            }else{
                throw new UsernameNotFoundException("User not found");
            }
        }catch (UsernameNotFoundException |CustomExeption e){
            throw new CustomExeption(e.getMessage());
        }

    }

    @Override
    public String changePassword(String email, ChangePassword changePassword) {
        try{
            User user = userRepo.findByUsername(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }else{
                String password = bCryptPasswordEncoder.encode(changePassword.password());
                if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
                    throw new CustomExeption("Password and Confirm Password does not match");
                }

                if(bCryptPasswordEncoder.matches(changePassword.password(),user.getPassword())){
                    throw new CustomExeption("New Password cannot be same as old password");
                }
                List<PasswordHistory> passwordHistories = passwordHistoryRepo.findByUsername(email);
                if(!passwordHistories.isEmpty()){
                    for(PasswordHistory passwordHistory: passwordHistories){
                        if(bCryptPasswordEncoder.matches(changePassword.password(),passwordHistory.getPassword())){
                            throw new CustomExeption("Password already used");
                        }
                    }
                }
                ForgotPassword fp = forgotPasswordRepo.findByStatusAndUser_Username("OTP Verified",email);
                if(fp == null){
                    throw new CustomExeption("OTP not verified");
                }
                PasswordHistory passwordHistory = new PasswordHistory();
                passwordHistory.setUsername(email);
                passwordHistory.setPassword(user.getPassword());
                passwordHistoryRepo.save(passwordHistory);
                user.setPassword(password);
                user.setType(user.getType());
                userRepo.save(user);
                fp.setStatus("Success");
                forgotPasswordRepo.save(fp);

                return "Password Changed Successfully";
            }
        }catch (CustomExeption e){
            throw new CustomExeption(e.getMessage());
        }

    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }

    public User findOrCreateGoogleUser(String email, String googleId) {
        try{
            User user = userRepo.findByUsername(email);
            if(user != null){
                return user;
            }else {
                User newUser = new User();
                newUser.setUsername(email);
                newUser.setGoogleId(googleId);
                newUser.setType("CUSTOMER");
                newUser.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
                User user1 = userRepo.save(newUser);
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setUser(userMapper.toUserDTO(user1));
                customerDTO.setEmail(email);
                customerService.saveCustomer(customerDTO);
                return user1;
            }
        }catch (CustomExeption e){
            throw new CustomExeption(e.getMessage());
        }

    }
}

package com.dtech.Ecommerce.auth.authController;

import com.dtech.Ecommerce.auth.authService.Impl.UserImpl;
import com.dtech.Ecommerce.auth.dto.UserDTO;
import com.dtech.Ecommerce.auth.utill.ChangePassword;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class UserController {

    private final UserImpl userImpl;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        userImpl.register(userDTO);
        return ResponseEntity.ok("SignUp successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        userImpl.login(userDTO, response);
        return ResponseEntity.ok("Login successful");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response, @CookieValue(value = "jwtE", defaultValue = "") String jwtToken) {
        if (jwtToken.isEmpty()) {
            return ResponseEntity.badRequest().body("JWT token not found in cookies");
        }
        Cookie cookie = new Cookie("jwtE", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        userImpl.logout(jwtToken);
        return ResponseEntity.ok("Logout successful");
    }


    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email) {
        return new ResponseEntity<>(userImpl.forgotPassword(email), HttpStatus.OK);
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<?> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        return new ResponseEntity<>(userImpl.verifyOtp(otp,email), HttpStatus.OK);
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<?> changePassword(@PathVariable String email, @RequestBody ChangePassword changePassword) {
        return new ResponseEntity<>(userImpl.changePassword(email, changePassword), HttpStatus.OK);
    }
}

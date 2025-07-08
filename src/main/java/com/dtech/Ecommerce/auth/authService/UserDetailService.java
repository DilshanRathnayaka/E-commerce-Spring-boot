package com.dtech.Ecommerce.auth.authService;

import com.dtech.Ecommerce.auth.authModel.User;
import com.dtech.Ecommerce.auth.authModel.UserPrinciple;
import com.dtech.Ecommerce.auth.authRepo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrinciple(user);
    }

}

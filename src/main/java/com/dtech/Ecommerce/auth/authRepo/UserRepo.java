package com.dtech.Ecommerce.auth.authRepo;

import com.dtech.Ecommerce.auth.authModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    Optional<User> findByGoogleId(String googleId);

}

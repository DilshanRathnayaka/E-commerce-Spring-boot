package com.dtech.Ecommerce.auth.authRepo;

import com.dtech.Ecommerce.auth.authModel.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
    List<Token> findByUsername(String username);
}

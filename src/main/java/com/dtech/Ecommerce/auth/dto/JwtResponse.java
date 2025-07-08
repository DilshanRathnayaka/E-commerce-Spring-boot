package com.dtech.Ecommerce.auth.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String refreshToken;
    private Long expiresIn;
    private String username;

    public JwtResponse(String token,String username) {
        this.token = token;
        this.username = username;
        this.expiresIn = 604800L;
    }
}
package com.dtech.Ecommerce.auth.authService;

import com.dtech.Ecommerce.auth.authModel.Token;
import com.dtech.Ecommerce.auth.authRepo.TokenRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {
    @Autowired
    private TokenRepository tokenRepository;
    private final String secretKey = "aL7s3FvL1d8oK9nM2jQ5wT1yH4xZ0cR7bE6gN8hD3iJ9kU1mO4pV5tA2qW0eY";
    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println(base64Key);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
                .and()
                .signWith(getKey())
                .compact();

        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUsername(username);
        tokenEntity.setExpirationDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2));
        tokenEntity.setValid(true);

        tokenRepository.save(tokenEntity);

        return token;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        Token tokenEntity = tokenRepository.findByToken(token);
        if (tokenEntity == null || !tokenEntity.isValid()) {
            return false;
        }
        if (tokenEntity.getExpirationDate().before(new Date())) {
            return false;
        }
        return true;
    }

    public void invalidateToken(String token) {
        Token tokenEntity = tokenRepository.findByToken(token);
        if (tokenEntity != null) {
            tokenEntity.setValid(false);
            tokenRepository.save(tokenEntity);
        }
    }

    public boolean isTokenInDatabase(String token) {
        Token tokenEntity = tokenRepository.findByToken(token);
        return tokenEntity != null && tokenEntity.isValid();
    }

    private SecretKey getKey() {
        byte[] decodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            return new Date(0);
        }
    }

}

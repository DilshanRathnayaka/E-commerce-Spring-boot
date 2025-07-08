package com.dtech.Ecommerce.auth.authController;

import com.dtech.Ecommerce.auth.authModel.User;
import com.dtech.Ecommerce.auth.authService.JWTService;
import com.dtech.Ecommerce.auth.authService.UserService;
import com.dtech.Ecommerce.auth.dto.GoogleTokenRequest;
import com.dtech.Ecommerce.auth.dto.JwtResponse;
import com.dtech.Ecommerce.auth.utill.GoogleIdTokenVerifierUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuth2Controller {
    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping("/google/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestBody GoogleTokenRequest request, HttpServletResponse response) {
        // 1. Verify Google ID token
        GoogleIdToken.Payload payload = GoogleIdTokenVerifierUtil.verify(request.getCredential());

        // 2. Check if user exists
        User user = userService.findOrCreateGoogleUser(
                payload.getEmail(),
                payload.getSubject()
        );

        // 3. Generate JWT
        String token = jwtService.generateToken(user.getUsername(), null);

        // 4. Encode the token value
//        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);

        // 5. Set HTTP-only cookie
        Cookie cookie = new Cookie("jwtE", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(token, user.getUsername()));
    }
}
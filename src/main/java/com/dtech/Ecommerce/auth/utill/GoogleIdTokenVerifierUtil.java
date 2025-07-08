package com.dtech.Ecommerce.auth.utill;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Collections;


public class GoogleIdTokenVerifierUtil {
    private GoogleIdTokenVerifierUtil() {}

    public static GoogleIdToken.Payload verify(String idToken) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList("47324674105-ivu2tvgl2rupdnl8krqd4eaivs60197i.apps.googleusercontent.com")) // Replace this
                    .setIssuer("https://accounts.google.com")
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                throw new BadCredentialsException("Invalid Google ID token");
            }
            return googleIdToken.getPayload();
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to verify Google token", e);
        }
    }
}
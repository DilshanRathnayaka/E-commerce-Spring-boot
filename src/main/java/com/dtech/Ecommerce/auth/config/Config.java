package com.dtech.Ecommerce.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Config {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;
    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
                 .csrf(AbstractHttpConfigurer::disable)
                 .cors(Customizer.withDefaults())
                 .authorizeHttpRequests(request -> request
                         .requestMatchers("auth/user/register",
                                 "auth/user/login",
                                 "auth/user/forgot-password/**",
                                 "auth/user/verify-otp/**",
                                 "auth/user/change-password/**",
                                 "api/v1/product/getAll",
                                 "api/files/download/**",
                                 "api/v1/order/topSaleProducts",
                                 "api/v1/product/getByUuid/**",
                                 "api/v1/product/getCategory",
                                 "api/v1/mail/send",
                                 "api/v1/product/searchByQuery",
                                 "api/auth/google/callback",
                                 "api/v1/product/getReviewProduct/**")
                         .permitAll()
                         .anyRequest().authenticated())
                 .oauth2ResourceServer(oauth2 -> oauth2
                         .jwt(jwt -> jwt.decoder(jwtDecoder()))
                 )
                 .oauth2Login(Customizer.withDefaults())
//                 .httpBasic(Customizer.withDefaults())
                 .sessionManagement(session ->
                         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                 .build();
     }

     @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com").build();
    }
}

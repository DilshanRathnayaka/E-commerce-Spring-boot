package com.dtech.Ecommerce.auth.authService.Impl;

import com.dtech.Ecommerce.auth.dto.UserDTO;
import com.dtech.Ecommerce.auth.utill.ChangePassword;
import jakarta.servlet.http.HttpServletResponse;

public interface UserImpl {
    void register(UserDTO userDTO);

    void login(UserDTO userDTO, HttpServletResponse response);

    String logout(String response);

    String forgotPassword(String email);

    String verifyOtp(Integer otp, String email);

    String changePassword(String email, ChangePassword changePassword);
}


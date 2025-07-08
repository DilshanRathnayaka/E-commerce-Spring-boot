package com.dtech.Ecommerce.auth.authRepo;

import com.dtech.Ecommerce.auth.authModel.ForgotPassword;
import com.dtech.Ecommerce.auth.authModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Integer> {

    ForgotPassword findByOtpAndUserAndStatus(Integer otp, User user, String status);

    ForgotPassword findByStatusAndUser_Username(String status,String username);
}

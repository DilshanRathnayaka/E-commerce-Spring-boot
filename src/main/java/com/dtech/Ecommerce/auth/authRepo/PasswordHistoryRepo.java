package com.dtech.Ecommerce.auth.authRepo;

import com.dtech.Ecommerce.auth.authModel.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordHistoryRepo extends JpaRepository<PasswordHistory, Integer> {
    List<PasswordHistory> findByUsername(String username);
}

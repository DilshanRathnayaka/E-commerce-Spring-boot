package com.dtech.Ecommerce.cart.repository;

import com.dtech.Ecommerce.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByCustomer_id(Integer id);
}

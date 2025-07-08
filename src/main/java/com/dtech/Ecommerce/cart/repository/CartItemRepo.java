package com.dtech.Ecommerce.cart.repository;

import com.dtech.Ecommerce.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    CartItem findByUuidAndCart_Id(String uuid, Integer cartId);

    void deleteCartItemByCart_Id(Integer cartId);

    void deleteCartItemByUuidAndCart_Id(String uuid, Integer cartId);

}

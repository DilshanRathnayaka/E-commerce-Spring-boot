package com.dtech.Ecommerce.cart.service;

import com.dtech.Ecommerce.cart.dto.CartDTO;
import com.dtech.Ecommerce.cart.model.Cart;

public interface CartService {
    CartDTO updateCart(CartDTO cartDTO);

    Cart newCartId(Integer customerId);

    String clearCartItem(Integer id);

    String deleteCartItem(String uuid, Integer cartId);

    String increaseDecreaseQuantity(String uuid,Integer cartId,Integer quantity);

}

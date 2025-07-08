package com.dtech.Ecommerce.cart.controller;

import com.dtech.Ecommerce.cart.dto.CartDTO;
import com.dtech.Ecommerce.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody CartDTO cartDTO) {
        return new ResponseEntity<>(cartService.updateCart(cartDTO), HttpStatus.OK);
    }

    @PostMapping("clearById/{id}")
    public ResponseEntity<?> clearCartItem(@PathVariable Integer id) {
        return new ResponseEntity<>(cartService.clearCartItem(id), HttpStatus.OK);
    }

    @PostMapping("deleteById/{uuid}/{cartId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String uuid, @PathVariable Integer cartId) {
        return new ResponseEntity<>(cartService.deleteCartItem(uuid,cartId), HttpStatus.OK);
    }

    @PostMapping("/increase/{uuid}/{cartId}/{quantity}")
    public ResponseEntity<?> increaseQuantity(@PathVariable String uuid, @PathVariable Integer cartId, @PathVariable Integer quantity) {
        return new ResponseEntity<>(cartService.increaseDecreaseQuantity(uuid,cartId,quantity), HttpStatus.OK);
    }


}

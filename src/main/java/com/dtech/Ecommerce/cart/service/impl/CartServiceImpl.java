package com.dtech.Ecommerce.cart.service.impl;

import com.dtech.Ecommerce.cart.dto.CartDTO;
import com.dtech.Ecommerce.cart.mapper.CartMapper;
import com.dtech.Ecommerce.cart.model.Cart;
import com.dtech.Ecommerce.cart.model.CartItem;
import com.dtech.Ecommerce.cart.repository.CartItemRepo;
import com.dtech.Ecommerce.cart.repository.CartRepo;
import com.dtech.Ecommerce.cart.service.CartService;
import com.dtech.Ecommerce.exeption.CartExeption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    public CartDTO updateCart(CartDTO cartDTO) {
        try{
            Cart cart1  = cartMapper.toCart(cartDTO);
            Cart cart = cartRepo.findById(cartDTO.getId()).orElseThrow(() -> new CartExeption("Cart not found"));
            if(cart != null){
                for(CartItem cartItem : cart1.getCartItem()){
                    CartItem cartItem2 = cartItemRepo.findByUuidAndCart_Id(cartItem.getUuid(), cart.getId());
                    if(cartItem2 != null){
                        cartItem2.setQuantity(cartItem.getQuantity());
                        cartItemRepo.save(cartItem2);
                    }else{
                        cartItem.setId(null);
                        cartItem.setCart(cart);
                        cartItemRepo.save(cartItem);
                    }
                }
            }
          return cartMapper.toCartDTO(cart);
        }catch (CartExeption e){
            throw new CartExeption(e.getMessage());
        }

    }

    public Cart newCartId(Integer customerId) {
        try{
            Cart newCart = new Cart();
            Optional<Cart> cart = cartRepo.findByCustomer_id(customerId);
            return cart.orElseGet(() -> cartRepo.save(newCart));
        }catch (CartExeption e){
            throw new CartExeption(e.getMessage());
        }

    }

    @Override
    public String clearCartItem(Integer id) {
        try{
            cartItemRepo.deleteCartItemByCart_Id(id);
            return "Cart Item Cleared";
        }catch (CartExeption e){
            throw new CartExeption(e.getMessage());
        }

    }

    @Override
    public String deleteCartItem(String uuid,Integer cartId) {
        try{
            cartItemRepo.deleteCartItemByUuidAndCart_Id(uuid,cartId);
            return "Cart Item Deleted";
        }catch (CartExeption e){
            throw new CartExeption(e.getMessage());
        }
    }

    @Override
    public String increaseDecreaseQuantity(String uuid,Integer cartId,Integer quantity) {
        try{
           CartItem cartItem = cartItemRepo.findByUuidAndCart_Id(uuid,cartId);
            cartItem.setQuantity(quantity);
            cartItemRepo.save(cartItem);
            return "Quantity Increased";
        }catch (CartExeption e){
            throw new CartExeption(e.getMessage());
        }
    }

}

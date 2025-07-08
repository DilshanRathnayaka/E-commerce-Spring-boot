package com.dtech.Ecommerce.cart.mapper;

import com.dtech.Ecommerce.cart.dto.CartDTO;
import com.dtech.Ecommerce.cart.dto.CartItemDTO;
import com.dtech.Ecommerce.cart.model.Cart;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
     CartDTO toCartDTO(Cart cart);
     Cart toCart(CartDTO cartDTO);

     CartItemDTO toCartItemDTO(Cart cart);
     Cart toCartItem(CartItemDTO cartItemDTO);


}

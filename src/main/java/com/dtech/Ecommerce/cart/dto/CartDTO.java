package com.dtech.Ecommerce.cart.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CartDTO {
    private Integer id;
    private Date created;
    private Date updated;
    private String status;
    private List<CartItemDTO> cartItem;
}

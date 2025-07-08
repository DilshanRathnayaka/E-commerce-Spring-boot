package com.dtech.Ecommerce.cart.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Integer id;
    private String name;
    private Integer price;
    private String image;
    private Integer quantity;
    private String sku;
    private String uuid;
    private Integer variantId;
}

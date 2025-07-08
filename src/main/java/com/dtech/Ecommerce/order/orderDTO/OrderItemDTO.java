package com.dtech.Ecommerce.order.orderDTO;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Integer id;
    private Integer quantity;
    private Integer prodId;
    private Double price;
    private String name;
    private String image;
    private String sku;
    private Integer productId;
    private Integer variantId;
}

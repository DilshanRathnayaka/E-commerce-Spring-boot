package com.dtech.Ecommerce.paymentGateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Double amount;
    private Long quantity;
    private String name;
    private String currency;
}

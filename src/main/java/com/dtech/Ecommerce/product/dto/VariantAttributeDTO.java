package com.dtech.Ecommerce.product.dto;

import com.dtech.Ecommerce.common.Document;
import com.dtech.Ecommerce.product.model.DocumentData;
import lombok.Data;

@Data
public class VariantAttributeDTO {
    private Integer id;
    private String name;
    private StockDTO stock;
    private Document image;
    private double price;
    private Integer discount;
    private double discountPrice;
}

package com.dtech.Ecommerce.product.dto;

import com.dtech.Ecommerce.common.Document;
import lombok.Data;

import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:28 PM
 */
@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String uuid;
    private String description;
    private double price;
    private Integer discount;
    private double discountPrice;
    private Integer category;
    private Integer subCategory;
    private String brand;
    private List<Document> image;
    private StockDTO stock;
    private List<VariantAttributeDTO> variant;
}

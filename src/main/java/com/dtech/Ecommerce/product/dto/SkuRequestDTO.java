package com.dtech.Ecommerce.product.dto;

import lombok.Data;

import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:2/6/2025
 * Time:4:20 PM
 */
@Data
public class SkuRequestDTO {
    private String productName;
    private List<SpecificationDTO> specifications;
}

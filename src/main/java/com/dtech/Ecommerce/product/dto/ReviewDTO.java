package com.dtech.Ecommerce.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewDTO {
    private Integer id;
    private Integer productId;
    private Integer orderId;
    private Integer customerId;
    private String customerName;
    private Integer varientId;
    private double rate;
    private String comment;
    private List<ReviewImageDataDTO> images;
}

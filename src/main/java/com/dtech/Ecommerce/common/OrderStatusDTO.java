package com.dtech.Ecommerce.common;

import lombok.Data;

@Data
public class OrderStatusDTO {
    private String pending;
    private String shipped;
    private String delivered;
    private String cancelled;
}

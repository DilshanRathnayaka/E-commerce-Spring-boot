package com.dtech.Ecommerce.common;

import lombok.Data;

import java.util.Date;

@Data
public class SearchDTO {
    private Integer orderId;
    private String orderStatus;
    private Date fromDate;
    private Date toDate;
}

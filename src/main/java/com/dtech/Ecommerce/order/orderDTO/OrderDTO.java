package com.dtech.Ecommerce.order.orderDTO;


import com.dtech.Ecommerce.customer.customerDTO.CustomerDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Integer id;
    private Date orderDate;
    private String orderStatus;
    private String shippingMethod;
    private String paymentMethod;
    private Double orderTotal;
    private String shippingAddress;
    private CustomerDTO customer;
    private boolean review;
    private List<OrderItemDTO> orderItem;
}

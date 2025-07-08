package com.dtech.Ecommerce.order.orderModel;


import com.dtech.Ecommerce.customer.model.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date orderDate;
    private String orderStatus;
    private String shippingMethod;
    private String paymentMethod;
    private Double orderTotal;
    private String shippingAddress;
    private Integer prodId;
    private boolean review;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem;
}

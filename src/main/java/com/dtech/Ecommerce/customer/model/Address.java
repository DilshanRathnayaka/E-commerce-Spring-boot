package com.dtech.Ecommerce.customer.model;

import com.dtech.Ecommerce.order.orderModel.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String streetAddress;
    private String city;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country countryId;

    private String zipCode;

    private String state;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private String type;

    private String status;

}

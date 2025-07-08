package com.dtech.Ecommerce.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VariantAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL)
    private Stock stock;

    private double price;
    private Integer discount;
    private double discountPrice;

    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL)
    private DocumentData image;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

}

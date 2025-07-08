package com.dtech.Ecommerce.product.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:35 PM
 */
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private double price;

    private String uuid;

    private Integer category;
    private Integer subCategory;

    private Integer discount;

    private double discountPrice;

    private String brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentData> image;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Stock stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<VariantAttribute> variant;


}

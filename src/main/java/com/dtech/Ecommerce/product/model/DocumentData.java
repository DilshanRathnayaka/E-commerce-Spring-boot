package com.dtech.Ecommerce.product.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:36 PM
 */
@Data
@Entity
public class DocumentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String docUUID;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "id")
    private VariantAttribute variant;
}

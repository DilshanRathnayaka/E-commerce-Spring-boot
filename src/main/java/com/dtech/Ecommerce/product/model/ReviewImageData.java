package com.dtech.Ecommerce.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ReviewImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String docUUID;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}

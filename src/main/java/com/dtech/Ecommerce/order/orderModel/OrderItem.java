package com.dtech.Ecommerce.order.orderModel;

import com.dtech.Ecommerce.product.dto.ProductDTO;
import com.dtech.Ecommerce.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    private Double price;
    private Integer productId;
    private Integer variantId;
    private String name;
    private String image;
    private String sku;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}

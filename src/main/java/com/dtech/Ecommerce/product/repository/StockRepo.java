package com.dtech.Ecommerce.product.repository;

import com.dtech.Ecommerce.product.model.Product;
import com.dtech.Ecommerce.product.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:3:58 PM
 */
public interface StockRepo extends JpaRepository<Stock, Integer> {
    Stock findStockByVariant_Id(Integer variantId);

    Stock findStockByProduct_Id(Integer productId);
}

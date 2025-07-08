package com.dtech.Ecommerce.product.repository;

import com.dtech.Ecommerce.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:45 PM
 */
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByUuid(String uuid);

    List<Product> findByNameContainingIgnoreCase(String query);

    Page<Product> findByCategory(Integer id, Pageable pageable);

    Page<Product> findByCategoryAndSubCategory(Integer id, Integer subCategoryID, Pageable pageable);
}

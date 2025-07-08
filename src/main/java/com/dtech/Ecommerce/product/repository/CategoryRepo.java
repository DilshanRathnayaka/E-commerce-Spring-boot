package com.dtech.Ecommerce.product.repository;

import com.dtech.Ecommerce.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}

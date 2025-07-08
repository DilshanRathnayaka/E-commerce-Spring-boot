package com.dtech.Ecommerce.product.repository;

import com.dtech.Ecommerce.product.model.Category;
import com.dtech.Ecommerce.product.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepo extends JpaRepository<SubCategory, Integer> {

    List<SubCategory> findByCategory(Category category);

    List<SubCategory> findByCategory_Id(Integer id);
}

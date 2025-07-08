package com.dtech.Ecommerce.product.repository;

import com.dtech.Ecommerce.product.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Integer id);
}

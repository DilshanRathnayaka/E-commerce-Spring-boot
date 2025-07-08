package com.dtech.Ecommerce.product.repository;

import com.dtech.Ecommerce.product.model.VariantAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarientAttRepo extends JpaRepository<VariantAttribute, Integer> {
}

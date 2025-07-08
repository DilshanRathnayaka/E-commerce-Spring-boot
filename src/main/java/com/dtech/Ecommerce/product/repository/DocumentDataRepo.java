package com.dtech.Ecommerce.product.repository;


import com.dtech.Ecommerce.product.model.DocumentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDataRepo extends JpaRepository<DocumentData, Integer> {
    DocumentData findByDocUUID(String uuid);
}

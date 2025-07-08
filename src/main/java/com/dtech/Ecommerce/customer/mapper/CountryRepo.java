package com.dtech.Ecommerce.customer.mapper;

import com.dtech.Ecommerce.customer.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepo extends JpaRepository<Country, Integer> {
    Optional<Country> findCountryByCode(String code);
}

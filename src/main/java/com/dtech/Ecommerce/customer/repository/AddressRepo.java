package com.dtech.Ecommerce.customer.repository;

import com.dtech.Ecommerce.customer.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Integer> {
    List<Address> findAddressesByCustomer_IdAndStatus(Integer customerId, String status);
}

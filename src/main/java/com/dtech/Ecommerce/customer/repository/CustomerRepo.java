package com.dtech.Ecommerce.customer.repository;
import com.dtech.Ecommerce.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findCustomerByEmail(String email);
}

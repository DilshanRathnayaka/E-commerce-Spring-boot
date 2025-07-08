package com.dtech.Ecommerce.customer.mapper;

import com.dtech.Ecommerce.customer.customerDTO.CustomerDTO;
import com.dtech.Ecommerce.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
     CustomerDTO toCustomerDTO(Customer customer);
     Customer toCustomer(CustomerDTO customerDTO);
}

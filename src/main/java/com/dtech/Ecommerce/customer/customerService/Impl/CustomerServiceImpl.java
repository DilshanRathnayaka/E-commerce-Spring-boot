package com.dtech.Ecommerce.customer.customerService.Impl;

import com.dtech.Ecommerce.customer.customerDTO.AddressDTO;
import com.dtech.Ecommerce.customer.customerDTO.CustomerDTO;
import com.dtech.Ecommerce.fileService.dto.CommonResponse;

import java.util.List;

public interface CustomerServiceImpl {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CommonResponse getCustomerCount();

    CustomerDTO getCustomerByEmail(String email);

    List<AddressDTO> getCustomerAddresById(Integer id);

    CommonResponse deleteAddress(Integer id);
}

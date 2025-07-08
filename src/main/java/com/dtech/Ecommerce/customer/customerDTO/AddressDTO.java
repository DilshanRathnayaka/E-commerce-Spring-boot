package com.dtech.Ecommerce.customer.customerDTO;

import lombok.Data;

@Data
public class AddressDTO {
    private Integer id;
    private String streetAddress;
    private String city;
    private CountryDTO countryId;
    private String state;
    private String zipCode;
    private String type;
    private String status;
}

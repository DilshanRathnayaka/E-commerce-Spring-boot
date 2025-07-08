package com.dtech.Ecommerce.customer.customerDTO;

import com.dtech.Ecommerce.auth.dto.UserDTO;
import com.dtech.Ecommerce.cart.dto.CartDTO;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Integer id;
    private String title;
    private String fname;
    private String lname;
    private String companyName;
    private String email;
    private String phone;
    private UserDTO user;
    private List<AddressDTO> address;
    private CartDTO cart;
}

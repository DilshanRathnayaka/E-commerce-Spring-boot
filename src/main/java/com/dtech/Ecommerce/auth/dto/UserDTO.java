package com.dtech.Ecommerce.auth.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String type;
}

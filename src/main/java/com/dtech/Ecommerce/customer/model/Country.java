package com.dtech.Ecommerce.customer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
public class Country {
    @Id
    private Integer id;

    @Column(name = "code",unique = true)
    private String code;

    private String name;

    @Column(name = "phoneCode")
    private String phoneCode;

}

package com.dtech.Ecommerce.auth.authModel;

import com.dtech.Ecommerce.customer.model.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String type;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Customer customer;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ForgotPassword> forgotPassword;

    @Column(unique = true)
    private String googleId;


}

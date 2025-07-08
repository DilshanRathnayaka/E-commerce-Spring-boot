package com.dtech.Ecommerce.auth.authModel;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String username;
    private boolean isValid;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
}

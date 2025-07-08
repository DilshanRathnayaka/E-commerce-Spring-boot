package com.dtech.Ecommerce.mail;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Lob
    private String message;
}

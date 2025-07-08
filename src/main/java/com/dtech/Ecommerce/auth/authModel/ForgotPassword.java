package com.dtech.Ecommerce.auth.authModel;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false )
    private Integer otp;

    @Column(nullable = false )
    private Date expireTime;

    @Column(nullable = false )
    private Boolean isVerified;

    private String status;

    @ManyToOne
    private User user;
}

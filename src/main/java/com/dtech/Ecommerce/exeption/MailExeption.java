package com.dtech.Ecommerce.exeption;

public class MailExeption extends RuntimeException {
    public MailExeption(String message) {
        super(message);
    }
}

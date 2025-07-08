package com.dtech.Ecommerce.auth.dto;

import lombok.Builder;

@Builder
public record MailBody(String to,String email, String subject, String text) {
}

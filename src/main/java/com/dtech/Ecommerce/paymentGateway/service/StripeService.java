package com.dtech.Ecommerce.paymentGateway.service;

import com.dtech.Ecommerce.paymentGateway.dto.ProductRequest;
import com.dtech.Ecommerce.paymentGateway.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {
    @Value("${stripe.secretKey}")
    private String secretKey;

    public StripeResponse checkoutProduct(ProductRequest productRequest) {
        Stripe.apiKey = secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getName()).build();

        long roundedAmount = Math.round(productRequest.getAmount() * 100);

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                .setUnitAmount(roundedAmount)
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineData = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3001/payment")
                .setCancelUrl("http://localhost:8080/payment")
                .addLineItem(lineData)
                .build();
        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment Session Created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }

    public String getPaymentStatus(String sessionId) {
        Stripe.apiKey = secretKey;
        try {
            Session session = Session.retrieve(sessionId);
            return session.getPaymentStatus();
        } catch (StripeException e) {
            e.printStackTrace();
            return "error";
        }
    }
}

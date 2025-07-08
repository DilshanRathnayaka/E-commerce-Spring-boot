package com.dtech.Ecommerce.paymentGateway.controller;

import com.dtech.Ecommerce.paymentGateway.dto.ProductRequest;
import com.dtech.Ecommerce.paymentGateway.dto.StripeResponse;
import com.dtech.Ecommerce.paymentGateway.service.StripeService;
import com.stripe.net.StripeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }
    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProduct(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }

    @GetMapping("/payment-status/{sessionId}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String sessionId) {
        String status = stripeService.getPaymentStatus(sessionId);
        return ResponseEntity.ok(status);
    }
}

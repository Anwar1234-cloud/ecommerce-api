package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.dto.PaymentRequest;
import com.anwar.ecommerce_api.model.Payment;
import com.anwar.ecommerce_api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ✅ API #30 — Initiate payment
    @PostMapping("/initiate")
    public ResponseEntity<Payment> initiatePayment(Authentication authentication,
                                                   @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(
                paymentService.initiatePayment(authentication.getName(), request.getOrderId())
        );
    }

    // ✅ API #31 — Confirm payment
    @PostMapping("/confirm")
    public ResponseEntity<Payment> confirmPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.confirmPayment(request));
    }

    // ✅ API #32 — Get payment by order id
    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    // ✅ API #33 — Get all payments (Admin)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
package com.anwar.ecommerce_api.service;

import com.anwar.ecommerce_api.dto.PaymentRequest;
import com.anwar.ecommerce_api.model.*;
import com.anwar.ecommerce_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    //  Initiate payment
    public Payment initiatePayment(String email, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    //  Confirm payment
    public Payment confirmPayment(PaymentRequest request) {
        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found!"));

        payment.setTransactionId(request.getTransactionId());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());

        // Update order status to CONFIRMED
        Order order = payment.getOrder();
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

    //  Get payment by order id
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found!"));
    }

    //  Get all payments (Admin)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}

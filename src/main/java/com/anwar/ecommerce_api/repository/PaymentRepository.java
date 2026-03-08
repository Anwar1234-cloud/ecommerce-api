package com.anwar.ecommerce_api.repository;

import com.anwar.ecommerce_api.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payment by order id
    Optional<Payment> findByOrderId(Long orderId);
}

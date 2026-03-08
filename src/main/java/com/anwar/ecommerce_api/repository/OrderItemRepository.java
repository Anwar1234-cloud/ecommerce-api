package com.anwar.ecommerce_api.repository;

import com.anwar.ecommerce_api.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Get all items of a specific order
    List<OrderItem> findByOrderId(Long orderId);
}

package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.dto.OrderRequest;
import com.anwar.ecommerce_api.model.Order;
import com.anwar.ecommerce_api.model.OrderStatus;
import com.anwar.ecommerce_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //  API #24 — Place order
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(Authentication authentication,
                                            @RequestBody OrderRequest request) {
        return ResponseEntity.status(201).body(
                orderService.placeOrder(authentication.getName(), request)
        );
    }

    //  API #25 — Get my orders
    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication.getName()));
    }

    //  API #26 — Get order by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    //  API #27 — Cancel order
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    //  API #28 — Get all orders (Admin)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    //  API #29 — Update order status (Admin)
    @PutMapping("/status/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                   @RequestBody OrderRequest request) {
        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, OrderStatus.valueOf(request.getStatus()))
        );
    }
}
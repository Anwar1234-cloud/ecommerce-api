package com.anwar.ecommerce_api.service;

import com.anwar.ecommerce_api.dto.OrderRequest;
import com.anwar.ecommerce_api.model.*;
import com.anwar.ecommerce_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    //  Place order from cart
    public Order placeOrder(String email, OrderRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart is empty!"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());

        // Calculate total and create order items
        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            // Check stock
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }

            // Reduce stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            total += product.getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(total);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Clear cart after order placed
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    //  Get my orders
    public List<Order> getMyOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return orderRepository.findByUserId(user.getId());
    }

    //  Get order by id
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    //  Cancel order
    public Order cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled!");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    //  Get all orders (Admin)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //  Update order status (Admin)
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
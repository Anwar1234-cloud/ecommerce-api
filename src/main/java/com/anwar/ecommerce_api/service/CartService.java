package com.anwar.ecommerce_api.service;

import com.anwar.ecommerce_api.dto.CartRequest;
import com.anwar.ecommerce_api.model.*;
import com.anwar.ecommerce_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    //  Get cart of logged in user
    public Cart getCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    //  Add item to cart
    public Cart addToCart(String email, CartRequest request) {
        Cart cart = getCart(email);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        // Check if product already in cart
        CartItem existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (existingItem != null) {
            // Just increase quantity
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // Add new item
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        }

        return cartRepository.findById(cart.getId()).get();
    }

    //  Update cart item quantity
    public Cart updateCartItem(String email, Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found!"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return getCart(email);
    }

    //  Remove item from cart
    public Cart removeFromCart(String email, Long itemId) {
        cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found!"));
        cartItemRepository.deleteById(itemId);
        return getCart(email);
    }

    //  Clear entire cart
    public String clearCart(String email) {
        Cart cart = getCart(email);
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return "Cart cleared successfully!";
    }
}
package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.dto.CartRequest;
import com.anwar.ecommerce_api.model.Cart;
import com.anwar.ecommerce_api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //  API #19 — Get cart
    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }

    //  API #20 — Add to cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(Authentication authentication,
                                          @RequestBody CartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(authentication.getName(), request));
    }

    //  API #21 — Update cart item
    @PutMapping("/update/{itemId}")
    public ResponseEntity<Cart> updateCartItem(Authentication authentication,
                                               @PathVariable Long itemId,
                                               @RequestBody CartRequest request) {
        return ResponseEntity.ok(
                cartService.updateCartItem(authentication.getName(), itemId, request.getQuantity())
        );
    }

    //  API #22 — Remove item from cart
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Cart> removeFromCart(Authentication authentication,
                                               @PathVariable Long itemId) {
        return ResponseEntity.ok(
                cartService.removeFromCart(authentication.getName(), itemId)
        );
    }

    //  API #23 — Clear cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.clearCart(authentication.getName()));
    }
}
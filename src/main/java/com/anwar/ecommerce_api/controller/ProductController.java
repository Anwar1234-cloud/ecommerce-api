package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.dto.ProductRequest;
import com.anwar.ecommerce_api.model.Product;
import com.anwar.ecommerce_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ API #12 — Get all products (Public)
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ✅ API #13 — Get product by id (Public)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // ✅ API #14 — Get products by category (Public)
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductsByCategory(id));
    }

    // ✅ API #15 — Search products (Public)
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProducts(name));
    }

    // ✅ API #16 — Add product (Admin only)
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.status(201).body(productService.addProduct(request));
    }

    // ✅ API #17 — Update product (Admin only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // ✅ API #18 — Delete product (Admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
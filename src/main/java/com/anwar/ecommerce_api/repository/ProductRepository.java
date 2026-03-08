package com.anwar.ecommerce_api.repository;

import com.anwar.ecommerce_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Get all products by category
    List<Product> findByCategoryId(Long categoryId);

    // Search products by name (contains keyword)
    List<Product> findByNameContainingIgnoreCase(String name);
}

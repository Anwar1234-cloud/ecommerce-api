package com.anwar.ecommerce_api.service;

import com.anwar.ecommerce_api.dto.ProductRequest;
import com.anwar.ecommerce_api.model.Category;
import com.anwar.ecommerce_api.model.Product;
import com.anwar.ecommerce_api.repository.CategoryRepository;
import com.anwar.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ✅ Get product by id
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    // ✅ Get products by category
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // ✅ Search products by name
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // ✅ Add product (Admin)
    public Product addProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        return productRepository.save(product);
    }

    // ✅ Update product (Admin)
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        return productRepository.save(product);
    }

    // ✅ Delete product (Admin)
    public String deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
        productRepository.deleteById(id);
        return "Product deleted successfully!";
    }
}
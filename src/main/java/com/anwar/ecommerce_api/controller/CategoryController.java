package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.model.Category;
import com.anwar.ecommerce_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //  API #7 — Get all categories (Public)
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //  API #8 — Get category by id (Public)
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    //  API #9 — Add category (Admin only)
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestParam String name,
                                                @RequestParam String description) {
        return ResponseEntity.status(201).body(categoryService.addCategory(name, description));
    }

    //  API #10 — Update category (Admin only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestParam String name,
                                                   @RequestParam String description) {
        return ResponseEntity.ok(categoryService.updateCategory(id, name, description));
    }

    //  API #11 — Delete category (Admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}

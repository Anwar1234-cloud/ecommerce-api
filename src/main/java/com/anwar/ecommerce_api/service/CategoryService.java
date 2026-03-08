package com.anwar.ecommerce_api.service;

import com.anwar.ecommerce_api.exception.ResourceNotFoundException;
import com.anwar.ecommerce_api.model.Category;
import com.anwar.ecommerce_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // ✅ Get category by id
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
    }

    // ✅ Add category (Admin)
    public Category addCategory(String name, String description) {
        if (categoryRepository.existsByName(name)) {
            throw new ResourceNotFoundException("Category already exists!");
        }
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    // ✅ Update category (Admin)
    public Category updateCategory(Long id, String name, String description) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    // ✅ Delete category (Admin)
    public String deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        categoryRepository.deleteById(id);
        return "Category deleted successfully!";
    }
}
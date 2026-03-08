package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.model.User;
import com.anwar.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // ✅ API #34 — Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ✅ API #35 — Block user
    @PutMapping("/user/block/{id}")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setBlocked(true);
        userRepository.save(user);
        return ResponseEntity.ok("User blocked successfully!");
    }

    // ✅ API #36 — Unblock user
    @PutMapping("/user/unblock/{id}")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setBlocked(false);
        userRepository.save(user);
        return ResponseEntity.ok("User unblocked successfully!");
    }
}
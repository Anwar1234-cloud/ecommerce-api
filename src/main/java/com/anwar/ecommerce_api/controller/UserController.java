package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.model.User;
import com.anwar.ecommerce_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ API #3 — Get profile
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getProfile(email);
        return ResponseEntity.ok(user);
    }

    // ✅ API #4 — Update profile
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(Authentication authentication,
                                              @RequestParam String name) {
        String email = authentication.getName();
        User user = userService.updateProfile(email, name);
        return ResponseEntity.ok(user);
    }

    // ✅ API #5 — Change password
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(Authentication authentication,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        String email = authentication.getName();
        String message = userService.changePassword(email, oldPassword, newPassword);
        return ResponseEntity.ok(message);
    }

    // ✅ API #6 — Delete account
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(Authentication authentication) {
        String email = authentication.getName();
        String message = userService.deleteAccount(email);
        return ResponseEntity.ok(message);
    }
}

package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.dto.LoginRequest;
import com.anwar.ecommerce_api.dto.LoginResponse;
import com.anwar.ecommerce_api.dto.RegisterRequest;
import com.anwar.ecommerce_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ✅ API #1 — Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String message = authService.register(request);
        return ResponseEntity.status(201).body(message);
    }

    // ✅ API #2 — Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

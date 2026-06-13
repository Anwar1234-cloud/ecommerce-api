package com.anwar.ecommerce_api.service;

import com.anwar.ecommerce_api.dto.LoginRequest;
import com.anwar.ecommerce_api.dto.LoginResponse;
import com.anwar.ecommerce_api.dto.RegisterRequest;
import com.anwar.ecommerce_api.model.Role;
import com.anwar.ecommerce_api.model.User;
import com.anwar.ecommerce_api.repository.UserRepository;
import com.anwar.ecommerce_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    //  Register
    public String register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER); // default role is USER

        userRepository.save(user);

        return "User registered successfully!";
    }

    //  Login
    public LoginResponse login(LoginRequest request) {

        // Step 1: Authenticate email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Step 2: Load user from DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Step 3: Check if user is blocked
        if (user.isBlocked()) {
            throw new RuntimeException("Your account has been blocked!");
        }

        // Step 4: Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // Step 5: Return token + role
        return new LoginResponse(token, user.getRole().name(), "Login successful!");
    }
}
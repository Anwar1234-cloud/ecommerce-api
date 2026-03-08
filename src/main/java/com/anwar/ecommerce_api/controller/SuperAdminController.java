package com.anwar.ecommerce_api.controller;

import com.anwar.ecommerce_api.dto.RegisterRequest;
import com.anwar.ecommerce_api.model.Role;
import com.anwar.ecommerce_api.model.User;
import com.anwar.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ API #37 — Get all users
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ✅ API #38 — Create admin
    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        User admin = new User();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
        return ResponseEntity.status(201).body("Admin created successfully!");
    }

    // ✅ API #39 — Delete any user
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    // ✅ API #40 — Delete admin
    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (user.getRole() != Role.ADMIN) {
            return ResponseEntity.badRequest().body("This user is not an admin!");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Admin deleted successfully!");
    }
}
/*

        ---

        ## 📁 Your project should look like this now:
        ```
model/          ✅ (11 files)
repository/     ✅ (8 files)
security/       ✅ (4 files)
dto/            ✅ (7 files)
service/        ✅ (7 files)

controller/
        ├── AuthController.java         ✅
        ├── UserController.java         ✅
        ├── CategoryController.java     ✅
        ├── ProductController.java      ✅
        ├── CartController.java         ✅
        ├── OrderController.java        ✅
        ├── PaymentController.java      ✅
        ├── AdminController.java        ✅
        └── SuperAdminController.java   ✅

 */
package com.anwar.ecommerce_api.repository;

import com.anwar.ecommerce_api.model.User;
import com.anwar.ecommerce_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (used in login)
    Optional<User> findByEmail(String email);

    // Check if email already exists (used in register)
    boolean existsByEmail(String email);

    // Find all users by role (used by super admin)
    List<User> findByRole(Role role);
}

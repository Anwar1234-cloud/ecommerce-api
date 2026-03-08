package com.anwar.ecommerce_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // enables @PreAuthorize on controllers
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // ✅ new lambda style
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/product/all").permitAll()
                        .requestMatchers("/api/product/{id}").permitAll()        // ← add this
                        .requestMatchers("/api/product/category/**").permitAll() // ← add this
                        .requestMatchers("/api/product/search").permitAll()      // ← add this
                        .requestMatchers("/api/category/all").permitAll()
                        .requestMatchers("/api/category/{id}").permitAll()       // ← add this
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/superadmin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/swagger-ui/**").permitAll()      // ← add this
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
/*

        ---

        ## 💡 Key Concepts Explained

| Class | Purpose |
        |---|---|
        | `JwtUtil` | Creates and validates JWT tokens |
        | `UserDetailsServiceImpl` | Loads user from DB for Spring Security |
        | `JwtFilter` | Runs on every request, checks token |
        | `SecurityConfig` | Defines who can access which endpoint |

        ---

        ## 🔄 Request Flow Example

User sends: GET /api/user/profile
Header: Authorization: Bearer eyJhbGci...

JwtFilter runs →
extracts token → "eyJhbGci..."
extracts email → "john@example.com"
loads user from DB → User found ✅
validates token → valid ✅
sets authentication in SecurityContext ✅

Controller runs →
sees authenticated user ✅
returns profile data ✅
        ```

        ---

        ## 📁 Your project should look like this now:
        ```
model/          ✅ (11 files)
repository/     ✅ (8 files)

security/
        ├── JwtUtil.java                ✅
        ├── UserDetailsServiceImpl.java ✅
        ├── JwtFilter.java              ✅
        └── SecurityConfig.java         ✅
 */
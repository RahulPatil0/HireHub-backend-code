package com.hirehub.controller;

import com.hirehub.dto.JwtResponse;
import com.hirehub.dto.LoginRequest;
import com.hirehub.model.Role;
import com.hirehub.model.User;
import com.hirehub.repository.UserRepository;
import com.hirehub.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * ✅ Admin Login Endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 1️⃣ Fetch admin by email
            User admin = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            // 2️⃣ Ensure the user is an ADMIN
            if (!admin.getRole().equals(Role.ADMIN)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Only ADMIN can access this route");
            }

            // 3️⃣ Validate password using BCrypt
            if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid password");
            }

            // 4️⃣ Generate JWT
            String token = jwtService.generateToken(admin.getEmail(), admin.getRole().name());

            // 5️⃣ Return token and role
            return ResponseEntity.ok(new JwtResponse(token, admin.getRole().name()));

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong. Please try again.");
        }
    }
}

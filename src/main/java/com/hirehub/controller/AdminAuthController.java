package com.hirehub.controller;

import com.hirehub.dto.JwtResponse;
import com.hirehub.dto.LoginRequest;
import com.hirehub.model.Role;
import com.hirehub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminAuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Force ADMIN role
            request.setRole(Role.ADMIN);

            JwtResponse response = authService.login(request);
            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body("Login failed: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Something went wrong. Please try again.");
        }
    }
}

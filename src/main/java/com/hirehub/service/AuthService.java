package com.hirehub.service;

import com.hirehub.config.JwtService;
import com.hirehub.dto.JwtResponse;
import com.hirehub.dto.LoginRequest;
import com.hirehub.dto.RegisterRequest;
import com.hirehub.model.Role;
import com.hirehub.model.User;
import com.hirehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // ========================= REGISTER =========================
    public JwtResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail()
                        .replaceAll("\\s", "")
                        .replaceAll("\\u200B", "")
                        .toLowerCase())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword())) // ❗ DO NOT TRIM PASSWORD
                .role(request.getRole())
                .approved(false)
                .active(false)
                .documentsVerified(false)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole().name());

        return JwtResponse.builder()
                .token(token)
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }

    // ========================= LOGIN (ADMIN / OWNER / WORKER) =========================
    public JwtResponse login(LoginRequest request) {

        String cleanEmail = request.getEmail()
                .replaceAll("\\s", "")
                .replaceAll("\\u200B", "")
                .toLowerCase();

        Role requestedRole = request.getRole();
        if (requestedRole == null) {
            throw new RuntimeException("Role is required");
        }

        // STEP 1 — find user by email
        User user = userRepository.findByEmail(cleanEmail)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // STEP 2 — ensure selected role matches stored role
        if (user.getRole() != requestedRole) {
            throw new RuntimeException("Invalid email or role");
        }

        // STEP 3 — verify password (NO CLEANING)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return JwtResponse.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}

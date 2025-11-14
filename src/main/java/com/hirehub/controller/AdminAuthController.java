//package com.hirehub.controller;
//
//import com.hirehub.dto.JwtResponse;
//import com.hirehub.dto.LoginRequest;
//import com.hirehub.model.Role;
//import com.hirehub.model.User;
//import com.hirehub.repository.UserRepository;
//import com.hirehub.config.JwtService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/admin/auth")
//@RequiredArgsConstructor
//public class AdminAuthController {
//
//    private final UserRepository userRepository;
//    private final JwtService jwtService;
//    private final PasswordEncoder passwordEncoder;
//
//    // 🔥 THIS GENERATES THE CORRECT HASH FOR Master@123 WHEN APP STARTS
//    @PostConstruct
//    public void generateHashForMasterAdmin() {
//        String generatedHash = new BCryptPasswordEncoder().encode("Master@123");
//        System.out.println("================================================");
//        System.out.println("GENERATED HASH FOR PASSWORD Master@123:");
//        System.out.println(generatedHash);
//        System.out.println("================================================");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//
//        // 1. Fetch user by email
//        User admin = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//
//        // 2. Ensure user is ADMIN
//        if (!admin.getRole().equals(Role.ADMIN)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Only ADMIN can use this login");
//        }
//
//        // 3. DEBUG (RAW PASSWORD, HASH, LENGTH, MATCH)
//        System.out.println("================== DEBUG AUTH ==================");
//        System.out.println("RAW PASSWORD    = [" + request.getPassword() + "]");
//        System.out.println("RAW LENGTH      = " + request.getPassword().length());
//        System.out.println("HASH FROM DB    = [" + admin.getPassword() + "]");
//        System.out.println("HASH LENGTH     = " + admin.getPassword().length());
//        System.out.println("PASSWORD MATCH? = " +
//                passwordEncoder.matches(request.getPassword(), admin.getPassword()));
//        System.out.println("================================================");
//
//        // 4. Validate password
//        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid password");
//        }
//
//        // 5. Generate JWT
//        String token = jwtService.generateToken(admin.getEmail(), admin.getRole().name());
//
//        // 6. Return response
//        return ResponseEntity.ok(new JwtResponse(token, admin.getRole().name()));
//    }
//}
package com.hirehub.controller;

import com.hirehub.dto.JwtResponse;
import com.hirehub.dto.LoginRequest;
import com.hirehub.model.Role;
import com.hirehub.model.User;
import com.hirehub.repository.UserRepository;
import com.hirehub.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 1. Fetch user by email
        User admin = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // 2. Ensure user is ADMIN
        if (!admin.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Only ADMIN can use this login");
        }

        // 3. Validate password
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid password");
        }

        // 4. Generate JWT
        String token = jwtService.generateToken(admin.getEmail(), admin.getRole().name());

        // 5. Return response
        return ResponseEntity.ok(new JwtResponse(token, admin.getRole().name()));
    }
}

package com.hirehub.controller;

import com.hirehub.dto.JwtResponse;
import com.hirehub.dto.RegisterRequest;
import com.hirehub.dto.LoginRequest;
import com.hirehub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public JwtResponse register(@ModelAttribute RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}

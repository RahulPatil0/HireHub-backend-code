package com.hirehub.controller;

import com.hirehub.model.User;
import com.hirehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/temp")
@RequiredArgsConstructor
public class TempAdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/reset")
    public String resetPassword() {
        return userRepository.findByEmail("master.admin.hirehub@gmail.com")
                .map(user -> {
                    String newRawPassword = "Admin123"; // 🔥 new admin password

                    user.setPassword(passwordEncoder.encode(newRawPassword));
                    userRepository.save(user);

                    return "Admin password reset to: " + newRawPassword;
                })
                .orElse("Admin not found");
    }
}

package com.hirehub.dto;

import com.hirehub.model.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String phone;   // ✅ ADD THIS
    private Role role;
}

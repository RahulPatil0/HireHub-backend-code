package com.hirehub.dto;

import com.hirehub.model.Role;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private Role role; // MUST be Role enum for login to work
}

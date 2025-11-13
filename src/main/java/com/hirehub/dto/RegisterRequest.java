package com.hirehub.dto;

import com.hirehub.model.Role;
import lombok.*;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}

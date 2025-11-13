package com.hirehub.dto;

import lombok.*;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

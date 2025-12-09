package com.hirehub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtResponse {
    private String token;
    private Long userId;
    private String username;
    private String email;
    private String role;
}

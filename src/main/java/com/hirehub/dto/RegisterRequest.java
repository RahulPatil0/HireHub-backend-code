package com.hirehub.dto;

import com.hirehub.model.Role;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String phone;
    private Role role;

    private MultipartFile aadhaar;
    private MultipartFile pan;
    private MultipartFile profilePhoto;
}

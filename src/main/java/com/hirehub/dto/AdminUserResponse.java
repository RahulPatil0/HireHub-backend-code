package com.hirehub.dto;

import lombok.*;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private boolean active;
    private String role;

    private String aadhaarUrl;
    private String panUrl;
    private String profilePhotoUrl;

    // NEW → list of documents (dynamic)
    private List<Map<String, String>> documents;

}

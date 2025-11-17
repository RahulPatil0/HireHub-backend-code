package com.hirehub.dto;

import com.hirehub.model.User;
import lombok.*;

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

    public static AdminUserResponse fromEntity(User user) {
        if (user == null) {
            return null;
        }

        return AdminUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .active(user.isActive())
                .role(user.getRole() != null ? user.getRole().name() : "UNKNOWN")
                .build();
    }
}

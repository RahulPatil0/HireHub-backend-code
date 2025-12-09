package com.hirehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerProfileResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;

    // 🔥 NEW FIELDS
    private Double latitude;
    private Double longitude;
    private boolean locationEnabled;

    private String skill; // Carpenter, Painter, etc.

    // Future analytics
    private double rating;
}

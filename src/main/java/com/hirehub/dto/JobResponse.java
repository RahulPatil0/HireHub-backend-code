package com.hirehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private Long id;

    // High-level job info
    private String title;
    private String description;
    private String skillType;
    private String jobType;
    private String urgency;

    // Workers / money / timing
    private int requiredWorkers;
    private String duration;
    private LocalDate date;
    private LocalTime startTime;
    private double budgetPerWorker;
    private String notes;

    // Location
    private String address;
    private String city;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;

    // Status & relations
    private String status;
    private UserResponse owner;
    private List<UserResponse> workers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

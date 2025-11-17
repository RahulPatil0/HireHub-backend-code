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
    private String skillType;
    private int requiredWorkers;
    private String duration;
    private LocalDate date;
    private LocalTime startTime;
    private double budgetPerWorker;
    private String notes;
    private String address;
    private double latitude;
    private double longitude;
    private String status;
    private UserResponse owner;
    private List<UserResponse> workers; // can be empty list
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

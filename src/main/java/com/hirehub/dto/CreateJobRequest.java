package com.hirehub.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateJobRequest {

    private String title;
    private String description;
    private String skillType;
    private String jobType;
    private String urgency;

    private int requiredWorkers;
    private String duration;

    private LocalDate startDate;   // from frontend
    private LocalTime startTime;

    private Double budgetPerWorker;
    private String notes;

    // Location fields
    private String address;
    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;
}

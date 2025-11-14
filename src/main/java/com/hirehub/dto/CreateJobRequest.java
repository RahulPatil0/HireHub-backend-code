package com.hirehub.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateJobRequest {

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
}

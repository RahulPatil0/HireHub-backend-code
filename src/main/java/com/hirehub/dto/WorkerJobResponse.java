package com.hirehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerJobResponse {
    private Long jobId;
    private String skillType;
    private String address;
    private double budget;
    private String duration;
    private String status;
}

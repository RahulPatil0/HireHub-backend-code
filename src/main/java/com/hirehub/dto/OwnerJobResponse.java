package com.hirehub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OwnerJobResponse {
    private Long jobId;
    private String jobTitle;
    private String location;
    private Double wages;
    private String status;
    private LocalDateTime createdAt;
}

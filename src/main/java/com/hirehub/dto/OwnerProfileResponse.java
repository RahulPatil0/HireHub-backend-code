package com.hirehub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerProfileResponse {
    private Long ownerId;
    private String name;
    private String email;
    private String phone;
    private int totalJobs;
    private int activeJobs;
}

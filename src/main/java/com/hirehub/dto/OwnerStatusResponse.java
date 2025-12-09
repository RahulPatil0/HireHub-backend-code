package com.hirehub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OwnerStatusResponse {
    private Long jobId;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime updatedAt;
}


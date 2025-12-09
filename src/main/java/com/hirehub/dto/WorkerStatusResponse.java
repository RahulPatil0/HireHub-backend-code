package com.hirehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerStatusResponse {

    private Long workerId;

    private int totalJobsCompleted;
    private int totalJobsPending;
    private int totalJobsAssigned;
}

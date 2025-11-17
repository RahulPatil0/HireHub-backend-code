package com.hirehub.dto;

import com.hirehub.model.Job;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminJobResponse {

    private Long id;
    private String skillType;
    private int requiredWorkers;
    private String duration;
    private LocalDate date;
    private LocalTime startTime;
    private double budgetPerWorker;
    private String status;
    private String ownerName;

    public static AdminJobResponse fromEntity(Job job) {
        return AdminJobResponse.builder()
                .id(job.getId())
                .skillType(job.getSkillType())
                .requiredWorkers(job.getRequiredWorkers())
                .duration(job.getDuration())
                .date(job.getDate())
                .startTime(job.getStartTime())
                .budgetPerWorker(job.getBudgetPerWorker())
                .status(job.getStatus() != null ? job.getStatus().name() : "UNKNOWN")
                .ownerName(job.getOwner() != null ? job.getOwner().getUsername() : "N/A")
                .build();
    }
}

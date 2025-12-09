package com.hirehub.mapper;

import com.hirehub.dto.OwnerJobResponse;
import com.hirehub.dto.OwnerProfileResponse;
import com.hirehub.dto.OwnerStatusResponse;
import com.hirehub.model.Job;
import com.hirehub.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerMapper {

    public OwnerJobResponse toOwnerJobResponse(Job job) {
        return OwnerJobResponse.builder()
                .jobId(job.getId())
                .jobTitle(job.getSkillType())       // ✔ use skillType
                .location(job.getAddress())        // ✔ use address
                .wages(job.getBudgetPerWorker())   // ✔ use budgetPerWorker
                .status(job.getStatus().name())
                .createdAt(job.getCreatedAt())
                .build();
    }

    public OwnerProfileResponse toOwnerProfileResponse(User owner, List<Job> jobs) {
        return OwnerProfileResponse.builder()
                .ownerId(owner.getId())
                .name(owner.getUsername())  // ✔ your User has username, not name
                .email(owner.getEmail())
                .phone(owner.getPhone())
                .totalJobs(jobs.size())
                .activeJobs((int) jobs.stream()
                        .filter(j -> j.getStatus().name().equals("OPEN"))
                        .count())
                .build();
    }

    public OwnerStatusResponse toOwnerStatusResponse(Long jobId, String oldStatus, String newStatus) {
        return OwnerStatusResponse.builder()
                .jobId(jobId)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .updatedAt(java.time.LocalDateTime.now())
                .build();
    }
}

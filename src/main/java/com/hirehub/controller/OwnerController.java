package com.hirehub.controller;

import com.hirehub.dto.OwnerJobResponse;
import com.hirehub.dto.OwnerProfileResponse;
import com.hirehub.dto.OwnerStatusResponse;
import com.hirehub.dto.CreateJobRequest;
import com.hirehub.model.Job;
import com.hirehub.service.JobService;
import com.hirehub.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OwnerController {

    private final OwnerService ownerService;
    private final JobService jobService;

    // 0️⃣ Create a new job (this is what triggers nearby worker notifications)
    @PostMapping("/{ownerId}/jobs")
    public OwnerJobResponse createJob(
            @PathVariable Long ownerId,
            @RequestBody CreateJobRequest request
    ) {
        // This will save job + (in JobServiceImpl) notify nearby workers
        Job job = jobService.createJob(request, ownerId);

        // Map Job → OwnerJobResponse (same style as OwnerMapper)
        return OwnerJobResponse.builder()
                .jobId(job.getId())
                .jobTitle(job.getSkillType())
                .location(job.getAddress())
                .wages(job.getBudgetPerWorker())
                .status(job.getStatus().name())
                .createdAt(job.getCreatedAt())
                .build();
    }

    // 1️⃣ View all jobs posted by owner
    @GetMapping("/{ownerId}/jobs")
    public List<OwnerJobResponse> getOwnerJobs(@PathVariable Long ownerId) {
        return ownerService.getOwnerJobs(ownerId);
    }

    // 2️⃣ View owner profile
    @GetMapping("/{ownerId}/profile")
    public OwnerProfileResponse getOwnerProfile(@PathVariable Long ownerId) {
        return ownerService.getOwnerProfile(ownerId);
    }

    // 3️⃣ Update job status (OPEN/CLOSED/COMPLETED)
    @PutMapping("/job/{jobId}/status")
    public OwnerStatusResponse updateJobStatus(
            @PathVariable Long jobId,
            @RequestParam String status) {
        return ownerService.updateJobStatus(jobId, status);
    }

    // 4️⃣ Delete job
    @DeleteMapping("/job/{jobId}")
    public String deleteJob(@PathVariable Long jobId) {
        ownerService.deleteJob(jobId);
        return "Job deleted successfully";
    }
}

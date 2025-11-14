package com.hirehub.controller;

import com.hirehub.dto.CreateJobRequest;
import com.hirehub.model.Job;
import com.hirehub.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // OWNER → POST JOB
    @PostMapping("/{ownerId}")
    public Job createJob(
            @PathVariable Long ownerId,
            @RequestBody CreateJobRequest request
    ) {
        return jobService.createJob(request, ownerId);
    }

    // WORKER → GET ALL JOBS
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // WORKER → FILTER BY SKILL
    @GetMapping("/skill/{skill}")
    public List<Job> getJobsBySkill(@PathVariable String skill) {
        return jobService.getJobsBySkill(skill);
    }

    // OWNER → VIEW JOBS THEY POSTED
    @GetMapping("/owner/{ownerId}")
    public List<Job> getJobsByOwner(@PathVariable Long ownerId) {
        return jobService.getJobsByOwner(ownerId);
    }

    // WORKER → APPLY FOR JOB
    @PostMapping("/{jobId}/apply/{workerId}")
    public Job applyForJob(
            @PathVariable Long jobId,
            @PathVariable Long workerId
    ) {
        return jobService.applyForJob(jobId, workerId);
    }
}

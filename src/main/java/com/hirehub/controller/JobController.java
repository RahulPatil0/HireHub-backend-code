package com.hirehub.controller;

import com.hirehub.dto.CreateJobRequest;
import com.hirehub.dto.JobResponse;
import com.hirehub.mapper.JobMapper;
import com.hirehub.model.Job;
import com.hirehub.model.User;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {

    private final JobService jobService;
    private final UserRepository userRepository;

    // 🟢 OWNER → CREATE JOB
    @PostMapping
    public JobResponse createJob(
            @RequestBody CreateJobRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized - Please login first");
        }

        String email = userDetails.getUsername();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (owner.getRole() != com.hirehub.model.Role.OWNER) {
            throw new RuntimeException("Only owners can post jobs");
        }

        Job savedJob = jobService.createJob(request, owner.getId());

        return JobMapper.toJobResponse(savedJob);
    }

    // 🟠 WORKER → GET ALL JOBS
    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs().stream()
                .map(JobMapper::toJobResponse)
                .toList();
    }

    // 🔵 GET JOBS BY SKILL
    @GetMapping("/skill/{skill}")
    public List<JobResponse> getJobsBySkill(@PathVariable String skill) {
        return jobService.getJobsBySkill(skill).stream()
                .map(JobMapper::toJobResponse)
                .toList();
    }

    // 🔴 OWNER → VIEW THEIR OWN JOBS
    @GetMapping("/owner/me")
    public List<JobResponse> getMyJobs(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            throw new RuntimeException("Unauthorized - Please login first");
        }

        String email = userDetails.getUsername();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobService.getJobsByOwner(owner.getId()).stream()
                .map(JobMapper::toJobResponse)
                .toList();
    }
}

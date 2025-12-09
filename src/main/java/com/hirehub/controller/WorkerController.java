package com.hirehub.controller;

import com.hirehub.dto.WorkerProfileResponse;
import com.hirehub.dto.WorkerJobResponse;
import com.hirehub.dto.WorkerApplyRequest;
import com.hirehub.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('WORKER')")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public String getWorkerInfo() {
        return "Worker API accessed successfully!";
    }

    @GetMapping("/{workerId}/profile")
    public ResponseEntity<WorkerProfileResponse> getWorkerProfile(@PathVariable Long workerId) {
        WorkerProfileResponse profile = workerService.getWorkerProfile(workerId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<WorkerJobResponse>> getAvailableJobs() {
        List<WorkerJobResponse> jobs = workerService.getAvailableJobs();
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(@RequestBody WorkerApplyRequest request) {
        boolean applied = workerService.applyForJob(request);
        return applied
                ? ResponseEntity.ok("Job application submitted successfully.")
                : ResponseEntity.badRequest().body("Failed to apply for job.");
    }

    @PutMapping("/{workerId}/update-profile")
    public ResponseEntity<String> updateProfile(
            @PathVariable Long workerId,
            @RequestBody WorkerProfileResponse profile) {

        boolean updated = workerService.updateWorkerProfile(workerId, profile);
        return updated
                ? ResponseEntity.ok("Worker profile updated successfully.")
                : ResponseEntity.badRequest().body("Failed to update profile.");
    }
    @GetMapping("/{workerId}/jobs/nearby")
    public ResponseEntity<List<WorkerJobResponse>> getNearbyJobs(
            @PathVariable Long workerId,
            @RequestParam(defaultValue = "5") double radius
    ) {
        List<WorkerJobResponse> jobs = workerService.getNearbyJobs(workerId, radius);
        return ResponseEntity.ok(jobs);
    }


    // 🚀 NEW — Update Worker Location
    @PutMapping("/{workerId}/location")
    public ResponseEntity<String> updateLocation(
            @PathVariable Long workerId,
            @RequestBody Map<String, Double> coords
    ) {
        boolean updated = workerService.updateWorkerLocation(
                workerId,
                coords.get("latitude"),
                coords.get("longitude")
        );

        return updated
                ? ResponseEntity.ok("Location updated")
                : ResponseEntity.badRequest().body("Failed to update location");
    }
}

package com.hirehub.service.impl;

import com.hirehub.dto.CreateJobRequest;
import com.hirehub.model.*;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.JobService;
import com.hirehub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public Job createJob(CreateJobRequest request, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException("User is not an owner");
        }

        // 🚀 Direct field mapping (no request.getLocation())
        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .skillType(request.getSkillType())
                .jobType(request.getJobType())
                .urgency(request.getUrgency())

                .requiredWorkers(request.getRequiredWorkers())
                .duration(request.getDuration())
                .date(request.getStartDate())
                .startTime(request.getStartTime())

                .budgetPerWorker(request.getBudgetPerWorker())
                .notes(request.getNotes())

                // 🟢 Direct mapping from request
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())

                .latitude(request.getLatitude())
                .longitude(request.getLongitude())

                .owner(owner)
                .status(JobStatus.OPEN)
                .build();

        Job savedJob = jobRepository.save(job);

        sendNearbyWorkerNotifications(savedJob);

        return savedJob;
    }

    // Worker Notification System
    private void sendNearbyWorkerNotifications(Job job) {
        if (job.getLatitude() == null || job.getLongitude() == null) {
            System.out.println("⚠ No coordinates provided. Skipping notification.");
            return;
        }

        List<User> workers = userRepository.findByRole(Role.WORKER);

        List<User> nearbyWorkers = workers.stream()
                .filter(w -> w.getLatitude() != null && w.getLongitude() != null)
                .filter(w -> {
                    double distance = distanceKm(
                            job.getLatitude(), job.getLongitude(),
                            w.getLatitude(), w.getLongitude()
                    );
                    return distance <= 10;
                })
                .toList();

        if (!nearbyWorkers.isEmpty()) {
            notificationService.notifyNearbyWorkers(nearbyWorkers, job);
        }
    }

    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> getJobsBySkill(String skillType) {
        return jobRepository.findBySkillTypeIgnoreCase(skillType);
    }

    @Override
    public List<Job> getJobsByOwner(Long ownerId) {
        return jobRepository.findByOwnerId(ownerId);
    }

    @Override
    public Job applyForJob(Long jobId, Long workerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (worker.getRole() != Role.WORKER) {
            throw new RuntimeException("User is not a worker");
        }

        if (job.getWorkers().size() >= job.getRequiredWorkers()) {
            throw new RuntimeException("All worker slots are already filled");
        }

        job.getWorkers().add(worker);

        if (job.getWorkers().size() == job.getRequiredWorkers()) {
            job.setStatus(JobStatus.ASSIGNED);
        }

        return jobRepository.save(job);
    }
}

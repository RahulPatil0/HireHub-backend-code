package com.hirehub.service.impl;

import com.hirehub.dto.WorkerApplyRequest;
import com.hirehub.dto.WorkerJobResponse;
import com.hirehub.dto.WorkerProfileResponse;
import com.hirehub.dto.WorkerStatusResponse;
import com.hirehub.mapper.WorkerMapper;
import com.hirehub.model.Job;
import com.hirehub.model.User;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final WorkerMapper workerMapper;

    // -----------------------------------------
    // 1️⃣ Worker Profile
    // -----------------------------------------
    @Override
    public WorkerProfileResponse getWorkerProfile(Long workerId) {
        return userRepository.findById(workerId)
                .map(workerMapper::toWorkerProfileResponse)
                .orElse(null);
    }

    // -----------------------------------------
    // 2️⃣ AVAILABLE JOBS (NOT USED NOW)
    // -----------------------------------------
    @Override
    public List<WorkerJobResponse> getAvailableJobs() {
        return new ArrayList<>();
    }

    // -----------------------------------------
    // 3️⃣ APPLY FOR JOB (PLACEHOLDER)
    // -----------------------------------------
    @Override
    public boolean applyForJob(WorkerApplyRequest request) {
        return true;
    }

    // -----------------------------------------
    // 4️⃣ UPDATE WORKER PROFILE
    // -----------------------------------------
    @Override
    public boolean updateWorkerProfile(Long workerId, WorkerProfileResponse profile) {
        Optional<User> userOpt = userRepository.findById(workerId);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        user.setUsername(profile.getUsername());
        user.setPhone(profile.getPhone());
        user.setEmail(profile.getEmail());

        // newly added fields
        user.setSkill(profile.getSkill());
        user.setLocationEnabled(profile.isLocationEnabled());
        user.setLatitude(profile.getLatitude());
        user.setLongitude(profile.getLongitude());

        userRepository.save(user);
        return true;
    }

    // -----------------------------------------
    // 5️⃣ WORKER STATUS
    // -----------------------------------------
    @Override
    public WorkerStatusResponse getWorkerStatus(Long workerId) {
        return WorkerStatusResponse.builder()
                .workerId(workerId)
                .totalJobsAssigned(0)
                .totalJobsPending(0)
                .totalJobsCompleted(0)
                .build();
    }

    // -----------------------------------------
    // 6️⃣ UPDATE LIVE LOCATION
    // -----------------------------------------
    @Override
    public boolean updateWorkerLocation(Long workerId, Double latitude, Double longitude) {
        User user = userRepository.findById(workerId).orElse(null);
        if (user == null) return false;

        user.setLatitude(latitude);
        user.setLongitude(longitude);
        user.setLocationEnabled(true);

        userRepository.save(user);
        return true;
    }

    // -----------------------------------------
    // 🚀 7️⃣ GET NEARBY JOBS BASED ON DISTANCE + SKILL + ACTIVE
    // -----------------------------------------
    @Override
    public List<WorkerJobResponse> getNearbyJobs(Long workerId, double radiusKm) {
        User worker = userRepository.findById(workerId).orElse(null);

        if (worker == null || worker.getLatitude() == null || !worker.isLocationEnabled()) {
            return new ArrayList<>();
        }

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .filter(job -> job.getLatitude() != null
                        && job.getSkillType().equalsIgnoreCase(worker.getSkill())) // match skill
                .map(job -> new AbstractMap.SimpleEntry<>(
                        job,
                        calculateDistance(
                                worker.getLatitude(), worker.getLongitude(),
                                job.getLatitude(), job.getLongitude()
                        )
                ))
                .filter(entry -> entry.getValue() <= radiusKm)
                .map(entry -> new WorkerJobResponse(
                        entry.getKey().getId(),
                        entry.getKey().getSkillType(),
                        entry.getKey().getAddress(),
                        entry.getKey().getBudgetPerWorker(),
                        entry.getKey().getDuration(),
                        entry.getKey().getStatus().name()
                ))
                .collect(Collectors.toList());
    }

    // -----------------------------------------
    // 🌍 Distance Calculation (Haversine)
    // -----------------------------------------
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}

package com.hirehub.service.impl;

import com.hirehub.dto.AdminJobResponse;
import com.hirehub.dto.AdminUserResponse;
import com.hirehub.mapper.AdminMapper;
import com.hirehub.model.*;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // ✅ Prevents LazyInitializationException
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    // -----------------------------
    // USER MANAGEMENT
    // -----------------------------

    @Override
    public List<AdminUserResponse> getAllWorkers() {
        return userRepository.findByRole(Role.WORKER)
                .stream()
                .map(AdminMapper::toAdminUser)
                .toList();
    }

    @Override
    public List<AdminUserResponse> getAllOwners() {
        return userRepository.findByRole(Role.OWNER)
                .stream()
                .map(AdminMapper::toAdminUser)
                .toList();
    }

    // ✅ Unified activate/deactivate/delete methods (used by frontend)
    @Override
    public AdminUserResponse activateUser(String type, Long userId) {
        return updateUserStatus(type, userId, true);
    }

    @Override
    public AdminUserResponse deactivateUser(String type, Long userId) {
        return updateUserStatus(type, userId, false);
    }

    @Override
    public AdminUserResponse deleteUser(String type, Long userId) {
        User user = findUserByTypeAndId(type, userId);
        userRepository.delete(user);
        return AdminMapper.toAdminUser(user);
    }

    // ✅ Internal helper — reuses logic for workers & owners
    private AdminUserResponse updateUserStatus(String type, Long userId, boolean active) {
        User user = findUserByTypeAndId(type, userId);
        user.setActive(active);
        userRepository.save(user);
        return AdminMapper.toAdminUser(user);
    }

    // ✅ Helper to safely locate user type (worker or owner)
    private User findUserByTypeAndId(String type, Long userId) {
        Role expectedRole;
        if ("workers".equalsIgnoreCase(type)) {
            expectedRole = Role.WORKER;
        } else if ("owners".equalsIgnoreCase(type)) {
            expectedRole = Role.OWNER;
        } else {
            throw new IllegalArgumentException("Invalid user type: " + type);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

        if (!user.getRole().equals(expectedRole)) {
            throw new IllegalStateException("User role mismatch for ID " + userId);
        }

        return user;
    }

    // -----------------------------
    // (LEGACY METHODS)
    // Keeping for backward compatibility — can delete later
    // -----------------------------
    @Override
    public AdminUserResponse approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setActive(true);
        userRepository.save(user);
        return AdminMapper.toAdminUser(user);
    }

    @Override
    public AdminUserResponse blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setActive(false);
        userRepository.save(user);
        return AdminMapper.toAdminUser(user);
    }

    @Override
    public AdminUserResponse deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
        return AdminMapper.toAdminUser(user);
    }

    // -----------------------------
    // JOB MANAGEMENT
    // -----------------------------
    @Override
    public List<AdminJobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(AdminMapper::toAdminJob)
                .toList();
    }

    @Override
    public AdminJobResponse approveJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
        job.setStatus(JobStatus.OPEN); // ✅ Or APPROVED depending on your enum
        jobRepository.save(job);
        return AdminMapper.toAdminJob(job);
    }

    @Override
    public AdminJobResponse rejectJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
        job.setStatus(JobStatus.CANCELLED); // ✅ Or REJECTED depending on enum
        jobRepository.save(job);
        return AdminMapper.toAdminJob(job);
    }

    @Override
    public AdminJobResponse deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
        jobRepository.delete(job);
        return AdminMapper.toAdminJob(job);
    }
}

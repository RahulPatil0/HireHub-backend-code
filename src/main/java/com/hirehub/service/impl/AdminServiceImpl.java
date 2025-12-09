package com.hirehub.service.impl;

import com.hirehub.dto.AdminJobResponse;
import com.hirehub.dto.AdminUserResponse;
import com.hirehub.mapper.AdminMapper;
import com.hirehub.model.Job;
import com.hirehub.model.JobStatus;
import com.hirehub.model.Role;
import com.hirehub.model.User;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    // ============================
    // USER MANAGEMENT
    // ============================

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

    private AdminUserResponse updateUserStatus(String type, Long userId, boolean active) {
        User user = findUserByTypeAndId(type, userId);
        user.setActive(active);
        userRepository.save(user);
        return AdminMapper.toAdminUser(user);
    }

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

    // ============================
    // PENDING USERS
    // ============================

    @Override
    public List<AdminUserResponse> getPendingUsers() {
        return userRepository.findByDocumentsVerifiedFalse()
                .stream()
                .filter(user -> user.getRole() == Role.WORKER || user.getRole() == Role.OWNER)
                .map(AdminMapper::toAdminUser)
                .toList();
    }

    // ============================
    // JOB MANAGEMENT
    // ============================

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
        job.setStatus(JobStatus.OPEN);
        jobRepository.save(job);
        return AdminMapper.toAdminJob(job);
    }

    @Override
    public AdminJobResponse rejectJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
        job.setStatus(JobStatus.CANCELLED);
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

    // ============================
    // OWNER → JOB LIST
    // ============================

    @Override
    public List<AdminJobResponse> getJobsByOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        if (!owner.getRole().equals(Role.OWNER)) {
            throw new IllegalStateException("User is not an owner");
        }

        return jobRepository.findByOwner(owner)
                .stream()
                .map(AdminMapper::toAdminJob)
                .toList();
    }

    // ============================
    // DOCUMENT VERIFICATION
    // ============================

    @Override
    public Map<String, Object> getUserDocuments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Map<String, Object> documents = new HashMap<>();
        documents.put("verified", user.isDocumentsVerified());

        user.getDocuments().forEach(doc -> {
            documents.put(doc.getType().toLowerCase(), doc.getUrl());
        });

        return documents;
    }

    @Override
    public void approveUserDocuments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // ✅ This is the main "admin approved" action:
        user.setDocumentsVerified(true);
        user.setActive(true);  // allow login

        // We are intentionally not using the `approved` field anymore (legacy)
        userRepository.save(user);
    }

    @Override
    public void rejectUserDocuments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setDocumentsVerified(false);
        user.setActive(false); // block login
        userRepository.save(user);
    }

    // ============================
    // LEGACY COMPATIBILITY
    // ============================

    @Override
    public AdminUserResponse approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // ✅ For legacy calls: treat as full approval
        user.setActive(true);
        user.setDocumentsVerified(true);

        userRepository.save(user);
        return AdminMapper.toAdminUser(user);
    }

    @Override
    public AdminUserResponse blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setActive(false); // block login
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
}

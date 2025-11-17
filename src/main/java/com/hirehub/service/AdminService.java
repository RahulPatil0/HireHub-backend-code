package com.hirehub.service;

import com.hirehub.dto.AdminJobResponse;
import com.hirehub.dto.AdminUserResponse;
import java.util.List;

public interface AdminService {

    // -----------------------------
    // USER MANAGEMENT
    // -----------------------------
    List<AdminUserResponse> getAllWorkers();
    List<AdminUserResponse> getAllOwners();

    // ✅ For activate/deactivate/delete user
    AdminUserResponse activateUser(String type, Long userId);
    AdminUserResponse deactivateUser(String type, Long userId);
    AdminUserResponse deleteUser(String type, Long userId);

    // Legacy methods (optional, can be removed if not used anymore)
    AdminUserResponse approveUser(Long userId);
    AdminUserResponse blockUser(Long userId);
    AdminUserResponse deleteUser(Long userId); // optional - for compatibility

    // -----------------------------
    // JOB MANAGEMENT
    // -----------------------------
    List<AdminJobResponse> getAllJobs();
    AdminJobResponse approveJob(Long jobId);
    AdminJobResponse rejectJob(Long jobId);
    AdminJobResponse deleteJob(Long jobId);
    List<AdminJobResponse> getJobsByOwner(Long ownerId);

}

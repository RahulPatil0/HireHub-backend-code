//package com.hirehub.service;
//
//import com.hirehub.dto.AdminJobResponse;
//import com.hirehub.dto.AdminUserResponse;
//import java.util.List;
//
//public interface AdminService {
//
//    // -----------------------------
//    // USER MANAGEMENT
//    // -----------------------------
//    List<AdminUserResponse> getAllWorkers();
//    List<AdminUserResponse> getAllOwners();
//
//    // ✅ For activate/deactivate/delete user
//    AdminUserResponse activateUser(String type, Long userId);
//    AdminUserResponse deactivateUser(String type, Long userId);
//    AdminUserResponse deleteUser(String type, Long userId);
//
//    // Legacy methods (optional, can be removed if not used anymore)
//    AdminUserResponse approveUser(Long userId);
//    AdminUserResponse blockUser(Long userId);
//    AdminUserResponse deleteUser(Long userId); // optional - for compatibility
//
//    // -----------------------------
//    // JOB MANAGEMENT
//    // -----------------------------
//    List<AdminJobResponse> getAllJobs();
//    AdminJobResponse approveJob(Long jobId);
//    AdminJobResponse rejectJob(Long jobId);
//    AdminJobResponse deleteJob(Long jobId);
//    List<AdminJobResponse> getJobsByOwner(Long ownerId);
//
//}
package com.hirehub.service;

import com.hirehub.dto.AdminJobResponse;
import com.hirehub.dto.AdminUserResponse;

import java.util.List;
import java.util.Map;

public interface AdminService {

    // ==========================
    // USER MANAGEMENT
    // ==========================
    List<AdminUserResponse> getAllWorkers();
    List<AdminUserResponse> getAllOwners();

    AdminUserResponse activateUser(String type, Long userId);
    AdminUserResponse deactivateUser(String type, Long userId);
    AdminUserResponse deleteUser(String type, Long userId); // main delete method


    // Legacy / compatibility methods (used by older frontend parts if any)
    AdminUserResponse approveUser(Long userId);   // now: active = true, documentsVerified = true
    AdminUserResponse blockUser(Long userId);     // now: active = false
    AdminUserResponse deleteUser(Long userId);


    // ==========================
    // DOCUMENT VERIFICATION
    // ==========================
    Map<String, Object> getUserDocuments(Long userId);

    // After this: user can login (if AuthService checks active && documentsVerified)
    void approveUserDocuments(Long userId);

    // After this: user cannot login
    void rejectUserDocuments(Long userId);


    // ==========================
    // JOB MANAGEMENT
    // ==========================
    List<AdminJobResponse> getAllJobs();
    AdminJobResponse approveJob(Long jobId);
    AdminJobResponse rejectJob(Long jobId);
    AdminJobResponse deleteJob(Long jobId);

    // Pending = documents not yet verified
    List<AdminUserResponse> getPendingUsers();

    List<AdminJobResponse> getJobsByOwner(Long ownerId);
}

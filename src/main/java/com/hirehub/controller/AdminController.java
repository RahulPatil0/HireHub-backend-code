//package com.hirehub.controller;
//
//import com.hirehub.dto.AdminJobResponse;
//import com.hirehub.dto.AdminUserResponse;
//import com.hirehub.service.AdminService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping("/api/admin")
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final AdminService adminService;
//
//    // ---------------------------
//    // USER MANAGEMENT
//    // ---------------------------
//
//    @GetMapping("/workers")
//    public List<AdminUserResponse> getAllWorkers() {
//        return adminService.getAllWorkers();
//    }
//
//    @GetMapping("/owners")
//    public List<AdminUserResponse> getAllOwners() {
//        return adminService.getAllOwners();
//    }
//
//    // ✅ Activate user (maps frontend "Activate" button)
//    @PostMapping("/{type}/{id}/activate")
//    public AdminUserResponse activateUser(@PathVariable String type, @PathVariable Long id) {
//        return adminService.activateUser(type, id);
//    }
//
//    // ✅ Deactivate user (maps frontend "Deactivate" button)
//    @PostMapping("/{type}/{id}/deactivate")
//    public AdminUserResponse deactivateUser(@PathVariable String type, @PathVariable Long id) {
//        return adminService.deactivateUser(type, id);
//    }
//
//    // ✅ Delete user (matches frontend Delete)
//    @DeleteMapping("/{type}/{id}")
//    public AdminUserResponse deleteUser(@PathVariable String type, @PathVariable Long id) {
//        return adminService.deleteUser(type, id);
//    }
//
//    // ✅ New: View all jobs posted by a particular owner
//    @GetMapping("/owners/{ownerId}/jobs")
//    public List<AdminJobResponse> getJobsByOwner(@PathVariable Long ownerId) {
//        return adminService.getJobsByOwner(ownerId);
//    }
//
//    // ---------------------------
//    // JOB MANAGEMENT
//    // ---------------------------
//
//    @GetMapping("/jobs")
//    public List<AdminJobResponse> getAllJobs() {
//        return adminService.getAllJobs();
//    }
//
//    @PostMapping("/jobs/{id}/approve")
//    public AdminJobResponse approveJob(@PathVariable Long id) {
//        return adminService.approveJob(id);
//    }
//
//    @PostMapping("/jobs/{id}/reject")
//    public AdminJobResponse rejectJob(@PathVariable Long id) {
//        return adminService.rejectJob(id);
//    }
//
//    @DeleteMapping("/jobs/{id}")
//    public AdminJobResponse deleteJob(@PathVariable Long id) {
//        return adminService.deleteJob(id);
//    }
//}
package com.hirehub.controller;

import com.hirehub.dto.AdminJobResponse;
import com.hirehub.dto.AdminUserResponse;
import com.hirehub.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // =============================
    // USER MANAGEMENT
    // =============================

    @GetMapping("/workers")
    public List<AdminUserResponse> getAllWorkers() {
        return adminService.getAllWorkers();
    }

    @GetMapping("/owners")
    public List<AdminUserResponse> getAllOwners() {
        return adminService.getAllOwners();
    }

    // Activate User (by type: workers / owners)
    @PostMapping("/{type}/{id}/activate")
    public AdminUserResponse activateUser(@PathVariable String type, @PathVariable Long id) {
        return adminService.activateUser(type, id);
    }

    // Deactivate User
    @PostMapping("/{type}/{id}/deactivate")
    public AdminUserResponse deactivateUser(@PathVariable String type, @PathVariable Long id) {
        return adminService.deactivateUser(type, id);
    }

    // Delete User by type
    @DeleteMapping("/{type}/{id}")
    public AdminUserResponse deleteUser(@PathVariable String type, @PathVariable Long id) {
        return adminService.deleteUser(type, id);
    }

    // =============================
    // VIEW OWNER → JOBS
    // =============================

    @GetMapping("/owners/{ownerId}/jobs")
    public List<AdminJobResponse> getJobsByOwner(@PathVariable Long ownerId) {
        return adminService.getJobsByOwner(ownerId);
    }

    // =============================
    // DOCUMENT VERIFICATION
    // =============================

    @GetMapping("/users/{userId}/documents")
    public Object getUserDocuments(@PathVariable Long userId) {
        return adminService.getUserDocuments(userId);
    }

    @PostMapping("/users/{userId}/documents/approve")
    public String approveUserDocuments(@PathVariable Long userId) {
        adminService.approveUserDocuments(userId);
        return "Documents approved successfully";
    }

    @PostMapping("/users/{userId}/documents/reject")
    public String rejectUserDocuments(@PathVariable Long userId) {
        adminService.rejectUserDocuments(userId);
        return "Documents rejected";
    }

    // =============================
    // JOB MANAGEMENT
    // =============================

    @GetMapping("/jobs")
    public List<AdminJobResponse> getAllJobs() {
        return adminService.getAllJobs();
    }

    @PostMapping("/jobs/{id}/approve")
    public AdminJobResponse approveJob(@PathVariable Long id) {
        return adminService.approveJob(id);
    }

    @PostMapping("/jobs/{id}/reject")
    public AdminJobResponse rejectJob(@PathVariable Long id) {
        return adminService.rejectJob(id);
    }

    @DeleteMapping("/jobs/{id}")
    public AdminJobResponse deleteJob(@PathVariable Long id) {
        return adminService.deleteJob(id);
    }

    // Pending users for verification
    @GetMapping("/pending-users")
    public List<AdminUserResponse> getPendingUsers() {
        return adminService.getPendingUsers();
    }
}

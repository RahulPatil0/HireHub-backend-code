package com.hirehub.controller;

import com.hirehub.model.User;
import com.hirehub.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/workers")
    public List<User> getWorkers() {
        return adminService.getAllWorkers();
    }

    @PostMapping("/workers/{id}/activate")
    public String activateWorker(@PathVariable Long id) {
        return adminService.activateWorker(id);
    }

    @PostMapping("/workers/{id}/deactivate")
    public String deactivateWorker(@PathVariable Long id) {
        return adminService.deactivateWorker(id);
    }

    @DeleteMapping("/workers/{id}")
    public String deleteWorker(@PathVariable Long id) {
        return adminService.deleteWorker(id);
    }

    @GetMapping("/owners")
    public List<User> getOwners() {
        return adminService.getAllOwners();
    }

    @PostMapping("/owners/{id}/block")
    public String blockOwner(@PathVariable Long id) {
        return adminService.blockOwner(id);
    }

    @PostMapping("/owners/{id}/unblock")
    public String unblockOwner(@PathVariable Long id) {
        return adminService.unblockOwner(id);
    }

    @DeleteMapping("/owners/{id}")
    public String deleteOwner(@PathVariable Long id) {
        return adminService.deleteOwner(id);
    }
}
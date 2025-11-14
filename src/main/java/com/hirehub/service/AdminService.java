package com.hirehub.service;

import com.hirehub.model.User;
import com.hirehub.model.Role;
import com.hirehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllWorkers() {
        return userRepository.findByRole(Role.WORKER);
    }

    @Transactional
    public String activateWorker(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Worker not found"));
        user.setActive(true);
        userRepository.save(user);
        return "Worker Activated";
    }

    @Transactional
    public String deactivateWorker(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Worker not found"));
        user.setActive(false);
        userRepository.save(user);
        return "Worker Deactivated";
    }

    @Transactional
    public String deleteWorker(Long id) {
        userRepository.deleteById(id);
        return "Worker Deleted";
    }

    public List<User> getAllOwners() {
        return userRepository.findByRole(Role.OWNER);
    }

    @Transactional
    public String blockOwner(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        user.setActive(false);
        userRepository.save(user);
        return "Owner Blocked";
    }

    @Transactional
    public String unblockOwner(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        user.setActive(true);
        userRepository.save(user);
        return "Owner Unblocked";
    }

    @Transactional
    public String deleteOwner(Long id) {
        userRepository.deleteById(id);
        return "Owner Deleted";
    }
}
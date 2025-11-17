package com.hirehub.mapper;

import com.hirehub.dto.AdminJobResponse;
import com.hirehub.dto.AdminUserResponse;
import com.hirehub.model.Job;
import com.hirehub.model.User;

public class AdminMapper {

    public static AdminUserResponse toAdminUser(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.isActive(),
                user.getRole().name()
        );
    }

    public static AdminJobResponse toAdminJob(Job job) {
        return new AdminJobResponse(
                job.getId(),
                job.getSkillType(),
                job.getRequiredWorkers(),
                job.getDuration(),
                job.getDate(),
                job.getStartTime(),
                job.getBudgetPerWorker(),
                job.getStatus().name(),
                job.getOwner().getUsername()
        );
    }
}

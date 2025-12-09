package com.hirehub.mapper;

import com.hirehub.dto.AdminUserResponse;
import com.hirehub.dto.AdminJobResponse;
import com.hirehub.model.User;
import com.hirehub.model.Job;

import java.util.*;

public class AdminMapper {

    public static AdminUserResponse toAdminUser(User user) {

        List<Map<String, String>> docs = new ArrayList<>();

        if (user.getDocuments() != null) {
            user.getDocuments().forEach(doc -> {
                Map<String, String> map = new HashMap<>();
                map.put("type", doc.getType());
                map.put("url", doc.getUrl());
                docs.add(map);
            });
        }

        return AdminUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .active(user.isActive())
                .role(user.getRole().name())

                .aadhaarUrl(user.getAadhaarUrl())
                .panUrl(user.getPanUrl())
                .profilePhotoUrl(user.getProfilePhotoUrl())

                .documents(docs)
                .build();
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

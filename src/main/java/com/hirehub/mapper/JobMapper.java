package com.hirehub.mapper;

import com.hirehub.dto.JobResponse;
import com.hirehub.dto.UserResponse;
import com.hirehub.model.Job;
import com.hirehub.model.User;

import java.util.ArrayList;
import java.util.List;

public class JobMapper {

    public static UserResponse toUserResponse(User user) {
        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name()
        );
    }

    public static JobResponse toJobResponse(Job job) {
        JobResponse res = new JobResponse();

        // 🔹 Basic job data
        res.setId(job.getId());
        res.setTitle(job.getTitle());
        res.setDescription(job.getDescription());
        res.setSkillType(job.getSkillType());
        res.setJobType(job.getJobType());
        res.setUrgency(job.getUrgency());

        // 🔹 Worker & time info
        res.setRequiredWorkers(job.getRequiredWorkers());
        res.setDuration(job.getDuration());
        res.setDate(job.getDate());
        res.setStartTime(job.getStartTime());
        res.setBudgetPerWorker(job.getBudgetPerWorker());
        res.setNotes(job.getNotes());

        // 🔹 Location
        res.setAddress(job.getAddress());
        res.setCity(job.getCity());
        res.setState(job.getState());
        res.setPincode(job.getPincode());
        res.setLatitude(job.getLatitude());
        res.setLongitude(job.getLongitude());

        // 🔹 Status + timestamps
        res.setStatus(job.getStatus().name());
        res.setCreatedAt(job.getCreatedAt());
        res.setUpdatedAt(job.getUpdatedAt());

        // 🔹 Owner (Single Object)
        res.setOwner(toUserResponse(job.getOwner()));

        // 🔹 Worker List (DTOs)
        List<UserResponse> workerList =
                job.getWorkers() == null ? new ArrayList<>() :
                        job.getWorkers().stream()
                                .map(JobMapper::toUserResponse)
                                .toList();

        res.setWorkers(workerList);

        return res;
    }
}

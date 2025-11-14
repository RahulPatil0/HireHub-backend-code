package com.hirehub.service;

import com.hirehub.dto.CreateJobRequest;
import com.hirehub.model.Job;

import java.util.List;

public interface JobService {

    Job createJob(CreateJobRequest request, Long ownerId);

    List<Job> getAllJobs();

    List<Job> getJobsBySkill(String skillType);

    List<Job> getJobsByOwner(Long ownerId);

    Job applyForJob(Long jobId, Long workerId);
}

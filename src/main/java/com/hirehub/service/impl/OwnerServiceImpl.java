package com.hirehub.service.impl;

import com.hirehub.dto.OwnerJobResponse;
import com.hirehub.dto.OwnerProfileResponse;
import com.hirehub.dto.OwnerStatusResponse;
import com.hirehub.mapper.OwnerMapper;
import com.hirehub.model.Job;
import com.hirehub.model.User;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hirehub.model.JobStatus;   // use the exact package you saw


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final OwnerMapper ownerMapper;

    @Override
    public List<OwnerJobResponse> getOwnerJobs(Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<Job> jobs = jobRepository.findByOwner(owner);

        return jobs.stream()
                .map(ownerMapper::toOwnerJobResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OwnerProfileResponse getOwnerProfile(Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<Job> jobs = jobRepository.findByOwner(owner);

        return ownerMapper.toOwnerProfileResponse(owner, jobs);
    }

    @Override
    public OwnerStatusResponse updateJobStatus(Long jobId, String newStatus) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        String old = job.getStatus().name();

        job.setStatus(Enum.valueOf(JobStatus.class, newStatus.toUpperCase()));


        jobRepository.save(job);

        return ownerMapper.toOwnerStatusResponse(jobId, old, newStatus);
    }

    @Override
    public void deleteJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        jobRepository.delete(job);
    }
}


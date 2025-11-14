package com.hirehub.service.impl;

import com.hirehub.dto.CreateJobRequest;
import com.hirehub.model.*;
import com.hirehub.repository.JobRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public Job createJob(CreateJobRequest request, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException("User is not an owner");
        }

        Job job = Job.builder()
                .skillType(request.getSkillType())
                .requiredWorkers(request.getRequiredWorkers())
                .duration(request.getDuration())
                .date(request.getDate())
                .startTime(request.getStartTime())
                .budgetPerWorker(request.getBudgetPerWorker())
                .notes(request.getNotes())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .owner(owner)
                .status(JobStatus.OPEN)
                .build();

        return jobRepository.save(job);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> getJobsBySkill(String skillType) {
        return jobRepository.findBySkillTypeIgnoreCase(skillType);
    }

    @Override
    public List<Job> getJobsByOwner(Long ownerId) {
        return jobRepository.findByOwnerId(ownerId);
    }

    @Override
    public Job applyForJob(Long jobId, Long workerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (worker.getRole() != Role.WORKER) {
            throw new RuntimeException("User is not a worker");
        }

        if (job.getWorkers().size() >= job.getRequiredWorkers()) {
            throw new RuntimeException("All worker slots are already filled");
        }

        job.getWorkers().add(worker);

        if (job.getWorkers().size() == job.getRequiredWorkers()) {
            job.setStatus(JobStatus.ASSIGNED);
        }

        return jobRepository.save(job);
    }
}

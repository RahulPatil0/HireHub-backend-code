package com.hirehub.service;

import com.hirehub.dto.WorkerApplyRequest;
import com.hirehub.dto.WorkerJobResponse;
import com.hirehub.dto.WorkerProfileResponse;
import com.hirehub.dto.WorkerStatusResponse;

import java.util.List;

public interface WorkerService {

    WorkerProfileResponse getWorkerProfile(Long workerId);

    List<WorkerJobResponse> getAvailableJobs();

    boolean applyForJob(WorkerApplyRequest request);

    boolean updateWorkerProfile(Long workerId, WorkerProfileResponse profile);

    WorkerStatusResponse getWorkerStatus(Long workerId);

    // 🚀 NEW — Live location method
    boolean updateWorkerLocation(Long workerId, Double latitude, Double longitude);

    List<WorkerJobResponse> getNearbyJobs(Long workerId, double radius);

}

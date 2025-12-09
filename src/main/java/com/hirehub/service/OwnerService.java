package com.hirehub.service;

import com.hirehub.dto.OwnerJobResponse;
import com.hirehub.dto.OwnerProfileResponse;
import com.hirehub.dto.OwnerStatusResponse;

import java.util.List;

public interface OwnerService {

    List<OwnerJobResponse> getOwnerJobs(Long ownerId);

    OwnerProfileResponse getOwnerProfile(Long ownerId);

    OwnerStatusResponse updateJobStatus(Long jobId, String newStatus);

    void deleteJob(Long jobId);
}

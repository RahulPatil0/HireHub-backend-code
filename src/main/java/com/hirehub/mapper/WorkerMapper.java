package com.hirehub.mapper;

import com.hirehub.dto.WorkerProfileResponse;
import com.hirehub.model.User;
import org.springframework.stereotype.Component;

@Component
public class WorkerMapper {

    public WorkerProfileResponse toWorkerProfileResponse(User user) {
        WorkerProfileResponse response = new WorkerProfileResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        // 🔥 NEW: Map location values
        response.setLatitude(user.getLatitude());
        response.setLongitude(user.getLongitude());
        response.setLocationEnabled(user.isLocationEnabled());

        // 🔥 NEW: Map skill
        response.setSkill(user.getSkill());

        // Placeholder until rating feature is built
        response.setRating(0.0);

        return response;
    }
}

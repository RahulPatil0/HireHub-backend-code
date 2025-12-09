package com.hirehub.service.impl;

import com.hirehub.model.Job;
import com.hirehub.model.User;
import com.hirehub.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate template;

    @Override
    public void notifyNearbyWorkers(List<User> workers, Job job) {

        for (User worker : workers) {

            // using email as identifier (must match frontend subscription)
            template.convertAndSendToUser(
                    worker.getEmail(),                   // <-- WebSocket user key
                    "/queue/nearby-jobs",                // <-- destination path
                    new JobNotification(
                            job.getId(),
                            job.getSkillType(),
                            job.getAddress(),
                            job.getBudgetPerWorker()
                    )
            );

            System.out.println("📩 WebSocket sent to: " + worker.getEmail());
        }
    }

    @Data
    @AllArgsConstructor
    static class JobNotification {
        private Long jobId;
        private String skillType;
        private String location;
        private double budget;
    }
}

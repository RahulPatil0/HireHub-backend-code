package com.hirehub.service;

import com.hirehub.model.Job;
import com.hirehub.model.User;
import java.util.List;

public interface NotificationService {
    void notifyNearbyWorkers(List<User> workers, Job job);
}

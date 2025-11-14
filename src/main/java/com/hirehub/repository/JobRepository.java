package com.hirehub.repository;

import com.hirehub.model.Job;
import com.hirehub.model.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findBySkillTypeIgnoreCase(String skillType);

    List<Job> findByOwnerId(Long ownerId);

    List<Job> findByStatus(JobStatus status);
}

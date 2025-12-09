package com.hirehub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // High-level job info
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String skillType;
    private String jobType;   // One-Time, Daily, Monthly
    private String urgency;   // Low, Medium, High

    // Workers / money / timing
    private int requiredWorkers;
    private String duration;
    private LocalDate date;
    private LocalTime startTime;
    private Double budgetPerWorker;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Location
    private String address;
    private String city;
    private String state;
    private String pincode;

    @Column
    private Double latitude;      // must be Double to allow null

    @Column
    private Double longitude;     // must be Double to allow null

    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.OPEN;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "job_workers",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private List<User> workers = new ArrayList<>();

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (this.workers == null) this.workers = new ArrayList<>();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        if (this.workers == null) this.workers = new ArrayList<>();
    }
}

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

    // --------------------------------------
    // JOB MAIN DETAILS
    // --------------------------------------
    private String skillType;        // Mason, Painter, Cleaning…

    private int requiredWorkers;     // how many workers needed

    private String duration;         // full day / 8 hours / custom

    private LocalDate date;          // job date

    private LocalTime startTime;     // starting time

    private double budgetPerWorker;  // payment per worker

    @Column(columnDefinition = "TEXT")
    private String notes;            // extra instructions

    // --------------------------------------
    // LOCATION DETAILS (GPS + address)
    // --------------------------------------
    private String address;          // human readable

    private double latitude;         // GPS latitude
    private double longitude;        // GPS longitude

    // --------------------------------------
    // JOB STATUS
    // --------------------------------------
    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.OPEN;
    // OPEN, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED

    // --------------------------------------
    // RELATIONS WITH USER
    // --------------------------------------

    // OWNER who posted the job
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // WORKERS assigned to job
    @ManyToMany
    @JoinTable(
            name = "job_workers",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private List<User> workers = new ArrayList<>();

    // --------------------------------------
    // TIMESTAMPS
    // --------------------------------------
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}

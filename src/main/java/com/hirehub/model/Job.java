//package com.hirehub.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "jobs")
//public class Job {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // --------------------------------------
//    // JOB MAIN DETAILS
//    // --------------------------------------
//    private String skillType;
//    private int requiredWorkers;
//    private String duration;
//    private LocalDate date;
//    private LocalTime startTime;
//    private double budgetPerWorker;
//
//    @Column(columnDefinition = "TEXT")
//    private String notes;
//
//    // --------------------------------------
//    // LOCATION DETAILS
//    // --------------------------------------
//    private String address;
//    private double latitude;
//    private double longitude;
//
//    // --------------------------------------
//    // JOB STATUS
//    // --------------------------------------
//    @Enumerated(EnumType.STRING)
//    private JobStatus status = JobStatus.OPEN;
//
//    // --------------------------------------
//    // RELATIONS WITH USER
//    // --------------------------------------
//
//    // OWNER who posted the job
//    @ManyToOne
//    @JoinColumn(name = "owner_id")
//    private User owner;
//
//    // WORKERS assigned to job
//    @ManyToMany
//    @JoinTable(
//            name = "job_workers",
//            joinColumns = @JoinColumn(name = "job_id"),
//            inverseJoinColumns = @JoinColumn(name = "worker_id")
//    )
//    private List<User> workers = new ArrayList<>();
//
//    // --------------------------------------
//    // TIMESTAMPS
//    // --------------------------------------
//    private LocalDateTime createdAt = LocalDateTime.now();
//    private LocalDateTime updatedAt = LocalDateTime.now();
//}
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

    // Job details
    private String skillType;
    private int requiredWorkers;
    private String duration;
    private LocalDate date;
    private LocalTime startTime;
    private double budgetPerWorker;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Location
    private String address;
    private double latitude;
    private double longitude;

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
    private List<User> workers = new ArrayList<>(); // ensure not null

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // lifecycle callbacks to auto-populate timestamps
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

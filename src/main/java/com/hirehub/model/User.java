package com.hirehub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -----------------------------------
    // BASIC USER DETAILS
    // -----------------------------------
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    // ✅ Hide password from responses, but still accept during registration
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    private boolean active = true;

    @Enumerated(EnumType.STRING)
    private Role role; // OWNER, WORKER, ADMIN

    // -----------------------------------
    // JOB RELATIONS
    // -----------------------------------
    // ✅ OWNER → JOBS (One owner can post many jobs)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // prevents recursion & lazy loading exception
    private List<Job> postedJobs = new ArrayList<>();

    // ✅ WORKER ↔ JOBS (Many-to-Many)
    @ManyToMany(mappedBy = "workers", fetch = FetchType.LAZY)
    @JsonIgnore // prevents circular reference
    private List<Job> assignedJobs = new ArrayList<>();

    // -----------------------------------
    // UTILITY METHODS
    // -----------------------------------
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + active +
                ", role=" + role +
                '}';
    }
}

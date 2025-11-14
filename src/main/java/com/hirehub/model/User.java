//package com.hirehub.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "users")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String username;
//    private String email;
//    private String password;
//    private boolean active;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//}
package com.hirehub.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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
    private String username;

    private String email;

    private String phone; // recommended for worker contact

    private String password;

    private boolean active = true;

    @Enumerated(EnumType.STRING)
    private Role role; // OWNER, WORKER, ADMIN

    // -----------------------------------
    // JOBS POSTED BY OWNER
    // -----------------------------------
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Job> postedJobs = new ArrayList<>();

    // -----------------------------------
    // JOBS ASSIGNED TO WORKER
    // -----------------------------------
    @ManyToMany(mappedBy = "workers")
    private List<Job> assignedJobs = new ArrayList<>();
}

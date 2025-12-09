package com.hirehub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;   // AADHAR, PAN, ADDRESS_PROOF, PROFILE
    private String url;    // File path / Cloudinary URL / S3 URL

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

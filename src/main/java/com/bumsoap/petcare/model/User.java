package com.bumsoap.petcare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String gender;
    @Column(name = "mobile")
    private String phoneNumber;
    private String email;
    private String password;
    private String userType;
    private Boolean enabled;

    @CreationTimestamp
    private LocalDate createdAt;

    @Transient
    private String specialization;

    @Transient
    List<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JoinColumn(name = "photo_id")
    private Photo photo;
}

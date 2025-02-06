package com.bumsoap.petcare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
    private String mobile;
    @NaturalId
    private String email;
    private String password;
    private String userType;
    private Boolean enabled;

    @CreationTimestamp
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<VerifToken> verifTokens = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles",
            joinColumns
                    = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns
                    = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();

    public String createdAtMonth() {
        int monInt = createdAt.getMonthValue();
        int yearInt = createdAt.getYear();
        String monStr = String.format("%d-%02d월", yearInt, monInt);
        return monStr;
    }

    @Transient
    private String specialization;

    @Transient
    private List<Appointment> appointments = new ArrayList<>();

    @Transient
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JoinColumn(name = "photo_id")
    private Photo photo;
}

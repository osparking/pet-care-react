package com.bumsoap.petcare.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class DtoUser {
    private long id;
    private String lastName;
    private String firstName;
    private String gender;
    private String mobile;
    private String email;
    private String userType;
    private Boolean enabled;
    private String specialization;
    private LocalDate createdAt;
    private List<DtoAppointment> appointments;
    private List<DtoReview> reviews;
    private Set<String> roles;
    private long photoId;
    private byte[] photo;
    private double averageRating;
    private Long totalReviewers;
}

package com.bumsoap.petcare.dto;

import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.model.Review;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DtoUser {
    private long id;
    private String lastName;
    private String firstName;
    private String gender;
    private String phoneNumber;
    private String email;
    private String userType;
    private Boolean enabled;
    private String specialization;
    private LocalDate createdAt;
    private List<DtoAppointment> appointments;
    private List<DtoReview> reviews;
    private long photoId;
    private byte[] photo;
    private double averageRating;
}

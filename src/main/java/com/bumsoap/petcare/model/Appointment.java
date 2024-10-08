package com.bumsoap.petcare.model;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    private String appointmentNo;
    private LocalDate createdDate;

    @Column(name = "sender")
    @ManyToOne(fetch = FetchType.LAZY)
    private User patient;

    @Column(name = "recipient")
    @ManyToOne(fetch = FetchType.LAZY)
    private User veterinarian;
}

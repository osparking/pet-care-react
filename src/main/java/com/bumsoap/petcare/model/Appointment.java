package com.bumsoap.petcare.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    private String appointmentNo;
    private LocalDate createdDate;
    private User patient;
    private User recipient;
}

package com.bumsoap.petcare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void addPatient(User patient) {
        this.setPatient(patient);
        if (patient.getAppointments() == null) {
            patient.setAppointments(new ArrayList<>());
        }
        patient.getAppointments().add(this);
    }
    
}

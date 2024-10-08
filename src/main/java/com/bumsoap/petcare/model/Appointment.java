package com.bumsoap.petcare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

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

    @JoinColumn(name = "sender_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User patient;

    @JoinColumn(name = "recipient_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User veterinarian;

    public void addPatient(User patient) {
        this.setPatient(patient);
        if (patient.getAppointments() == null) {
            patient.setAppointments(new ArrayList<>());
        }
        patient.getAppointments().add(this);
    }

    public void addVeterinarian(Veterinarian veterinarian) {
        this.setPatient(veterinarian);
        if (veterinarian.getAppointments() == null) {
            veterinarian.setAppointments(new ArrayList<>());
        }
        veterinarian.getAppointments().add(this);
    }

    public void setAppointmentNo() {
        this.appointmentNo = String.valueOf(
                new Random().nextLong()).substring(1,11);
    }
}

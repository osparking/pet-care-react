package com.bumsoap.petcare.model;

import com.bumsoap.petcare.utils.StatusAppointment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"patient", "veterinarian"})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    @Column(unique = true)
    private String appointmentNo;
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    private StatusAppointment status;

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

    public void addVeterinarian(User veterinarian) {
        this.setVeterinarian(veterinarian);
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

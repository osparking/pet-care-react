package com.bumsoap.petcare.model;

import com.bumsoap.petcare.utils.StatusAppointment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String reason;
    @Column(unique = true)
    private String appointmentNo;

    @CreationTimestamp
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    private StatusAppointment status;

    @JoinColumn(name = "sender_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User patient;

    @JoinColumn(name = "recipient_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User veterinarian;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

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

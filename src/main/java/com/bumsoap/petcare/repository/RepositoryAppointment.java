package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.utils.StatusAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryAppointment extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentNo(String appointmentNo);

    boolean existsByPatientIdAndVeterinarianIdAndStatus(
            Long patId, Long vetId, StatusAppointment statusAppointment);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.veterinarian.id = :userId OR a.patient.id = :userId")
    List<Appointment> findAllByUserId(Long userId);

    List<Appointment> findByVeterinarianAndDate(User vet, LocalDate date);
}

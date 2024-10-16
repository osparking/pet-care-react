package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.utils.StatusAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAppointment extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentNo(String appointmentNo);

    boolean existsByPatientIdAndVeterinarianIdAndStatus(
            Long patId, Long vetId, StatusAppointment statusAppointment);
}

package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAppointment extends JpaRepository<Appointment, Long> {
}

package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPatient extends JpaRepository<Patient, Long> {
}

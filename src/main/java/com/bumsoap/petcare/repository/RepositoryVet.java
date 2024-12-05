package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryVet extends JpaRepository<Veterinarian, Long> {
    List<Veterinarian> findVeterinarianBySpecialization(String speciality);
}

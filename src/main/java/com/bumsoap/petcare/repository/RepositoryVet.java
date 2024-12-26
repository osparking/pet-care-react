package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryVet extends JpaRepository<Veterinarian, Long> {
    List<Veterinarian> findVeterinarianBySpecialization(String speciality);
    boolean existsBySpecialization(String speciality);
    @Query("SELECT DISTINCT v.specialization FROM Veterinarian v")
    List<String> getDistinctSpecial();
}

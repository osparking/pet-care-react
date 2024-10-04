package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryVet extends JpaRepository<Veterinarian, Long> {
}

package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPet extends JpaRepository<Pet, Long> { // Define JPA Repository interface for Pet entity{
}

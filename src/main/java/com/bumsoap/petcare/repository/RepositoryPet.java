package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryPet extends JpaRepository<Pet, Long> {
    List<Pet> getDistinctColors(); // Define JPA Repository interface for Pet entity{

    List<Pet> getDistinctTypes();

    List<Pet> getDistinctBreedsByType(String type);
}

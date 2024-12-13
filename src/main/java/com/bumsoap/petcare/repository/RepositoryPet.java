package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryPet extends JpaRepository<Pet, Long> {
    @Query("SELECT DISTINCT p.color FROM Pet p")
    List<String> getDistinctColors(); // Define JPA Repository interface for Pet entity{

    @Query("SELECT DISTINCT p.type FROM Pet p")
    List<String> getDistinctTypes();

    @Query("SELECT DISTINCT p.breed FROM Pet p")
    List<String> getDistinctBreeds();

    @Query("SELECT DISTINCT p.breed FROM Pet p WHERE p.type = :type")
    List<String> getDistinctBreedsByType(String type);
}

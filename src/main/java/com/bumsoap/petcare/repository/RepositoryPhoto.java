package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPhoto extends JpaRepository<Photo, Long> { // RepositoryPhoto interface extends JpaRepository interface with Long as the primary key type{
}

package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRepositoryReview extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r " +
            "WHERE r.veterinarian.id = :userId OR r.patient.id = :userId")
    Page<Review> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r " +
            "WHERE r.veterinarian.id = :userId OR r.patient.id = :userId")
    List<Review> findAllByUserId(@Param("userId") Long userId);

    List<Review> findAllByVeterinarianId(Long vetId);

    Optional<Review> findByPatientIdAndVeterinarianId(Long patId, Long vetId);

    Long countByVeterinarianId(long id);
}

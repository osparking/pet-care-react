package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRepositoryReview extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r " +
            "WHERE r.veterinarian.id = :userId OR r.patient.id = :userId")
    Page<Review> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}

package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRepositoryReview extends JpaRepository<Review, Long> {
    Page<Review> findAllByUserId(Long userId, PageRequest request);
}

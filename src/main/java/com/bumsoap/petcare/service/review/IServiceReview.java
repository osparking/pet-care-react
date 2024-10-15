package com.bumsoap.petcare.service.review;

import com.bumsoap.petcare.model.Review;
import org.springframework.data.domain.Page;

public interface IServiceReview {
    void saveReview(Long patId, Long vetId, Review review);
    double getAverageRatingForVet(Long vetId);
    void updateReview(Long patId, Review review);
    Page<Review> getAllReviewsByUserId(Long userId, int page, int size);
}

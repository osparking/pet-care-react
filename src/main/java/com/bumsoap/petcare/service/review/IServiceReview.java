package com.bumsoap.petcare.service.review;

import com.bumsoap.petcare.model.Review;
import com.bumsoap.petcare.request.RequestUpdateReview;
import org.springframework.data.domain.Page;

public interface IServiceReview {
    Review saveReview(Long patId, Long vetId, Review review);
    double getAverageRatingForVet(Long vetId);
    Review updateReview(Long reviewId, RequestUpdateReview review);

    Page<Review> getAllReviewsByUserId(Long userId, int page, int size);
}

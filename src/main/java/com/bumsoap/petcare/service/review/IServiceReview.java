package com.bumsoap.petcare.service.review;

import com.bumsoap.petcare.model.Review;

public interface IServiceReview {
    void saveReview(Long patId, Long vetId, Review review);
}

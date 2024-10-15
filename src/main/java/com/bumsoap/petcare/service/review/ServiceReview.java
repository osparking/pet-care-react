package com.bumsoap.petcare.service.review;

import com.bumsoap.petcare.model.Review;
import com.bumsoap.petcare.repository.IRepositoryReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceReview implements IServiceReview {

    private final IRepositoryReview repositoryReview;

    @Override
    public void saveReview(Long patId, Long vetId, Review review) {

    }

    @Override
    public double getAverageRatingForVet(Long vetId) {
        return 0;
    }

    @Override
    public void updateReview(Long patId, Review review) {

    }

    @Override
    public Page<Review> getAllReviewsByUserId(Long userId, int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return repositoryReview.findAllByUserId(userId, request);
    }
}

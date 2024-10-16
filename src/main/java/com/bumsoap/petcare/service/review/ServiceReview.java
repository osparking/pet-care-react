package com.bumsoap.petcare.service.review;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Review;
import com.bumsoap.petcare.repository.IRepositoryReview;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceReview implements IServiceReview {

    private final IRepositoryReview repositoryReview;

    @Override
    public void saveReview(Long patId, Long vetId, Review review) {

    }

    @Override
    public double getAverageRatingForVet(Long vetId) {
        List<Review> reviews = repositoryReview.findAllByVeterinarianId(vetId);
        return reviews.isEmpty() ? 0 :
                reviews.stream().mapToInt(Review::getStars).average().orElse(0.0);
    }

    @Override
    public void updateReview(Long patId, Review review) {
        repositoryReview.findById(patId)
                .ifPresentOrElse(updated -> {
                    updated.setStars(review.getStars());
                    updated.setComment(review.getComment());
                    repositoryReview.save(updated);
                }, () -> {
                    throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
                });
    }

    @Override
    public Page<Review> getAllReviewsByUserId(Long userId, int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return repositoryReview.findAllByUserId(userId, request);
    }
}

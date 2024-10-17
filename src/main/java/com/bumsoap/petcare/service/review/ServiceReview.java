package com.bumsoap.petcare.service.review;

import com.bumsoap.petcare.exception.AlreadyReviewedException;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Review;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.IRepositoryReview;
import com.bumsoap.petcare.repository.RepositoryAppointment;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.RequestUpdateReview;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.StatusAppointment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceReview implements IServiceReview {

    private final IRepositoryReview repositoryReview;
    private final RepositoryAppointment repositoryAppointment;
    private final RepositoryUser repositoryUser;

    @Override
    public Review saveReview(Long patId, Long vetId, Review review) {
//        1) 리뷰어/환자와 수의사가 같은지 검사한다.
        if (vetId.equals(patId)) {
            throw new IllegalArgumentException(FeedbackMessage.INVALID_VAT_ID);
        }
//        2) 리뷰어가 이 수의사에 대해 리뷰한 적이 있는지 검사한다.
        Optional<Review> existingReview =
                repositoryReview.findByPatientIdAndVeterinarianId(patId, vetId);
        if (existingReview.isPresent()) {
            throw new AlreadyReviewedException(FeedbackMessage.ALREADY_REVIEWED);
        }

//        3) 리뷰어가 이 수의사에게 완료된 진료를 받았는지 검사한다.
        boolean hasCompletedAppointment = repositoryAppointment
                .existsByPatientIdAndVeterinarianIdAndStatus(
                        patId, vetId, StatusAppointment.COMPLETED);
        if (!hasCompletedAppointment) {
            throw new IllegalStateException(
                    FeedbackMessage.COMPLETED_APPOINTMENT_REQUIRED);
        }

//        4) 리뷰어와 수의사 정보를 디비에서 읽어온다.
        User veterinarian = repositoryUser.findById(vetId).orElseThrow(
                () -> new ResourceNotFoundException(FeedbackMessage.VET_OR_PAT_NOT_FOUND));
        User patient = repositoryUser.findById(patId).orElseThrow(
                () -> new ResourceNotFoundException(FeedbackMessage.VET_OR_PAT_NOT_FOUND));

//        5) 리뷰어와 수의사를 리뷰 레코드에 대입한다.
        review.setPatient(patient);
        review.setVeterinarian(veterinarian);

//        6) Review 객체를 DB에 저장한다.
        return repositoryReview.save(review);
    }

    @Override
    public double getAverageRatingForVet(Long vetId) {
        List<Review> reviews = repositoryReview.findAllByVeterinarianId(vetId);
        return reviews.isEmpty() ? 0 :
                reviews.stream().mapToInt(Review::getStars).average().orElse(0.0);
    }

    @Override
    public Review updateReview(Long reviewId, RequestUpdateReview review) {
        var updated = repositoryReview.findById(reviewId).orElseThrow(() -> {
            throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
            });
        updated.setStars(review.getStars());
        updated.setComment(review.getComment());
        return repositoryReview.save(updated);
    }

    @Override
    public Page<Review> getAllReviewsByUserId(Long userId, int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return repositoryReview.findAllByUserId(userId, request);
    }

    @Override
    public void deleteReview(Long reviewId) throws ResourceNotFoundException  {
        Review review = repositoryReview.findById(reviewId).orElseThrow(() -> {
            throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
            });
        // delete relationship between Review and Users
        review.getPatient().getReviews().remove(review);
        review.getVeterinarian().getReviews().remove(review);

        repositoryReview.deleteById(reviewId);
    }
}

package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.exception.AlreadyReviewedException;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Review;
import com.bumsoap.petcare.request.RequestUpdateReview;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.review.IServiceReview;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping(UrlMapping.REVIEWS)
@RestController(value = "reviewController")
public class ControllerReview {

    private final IServiceReview serviceReview;

    @PutMapping(UrlMapping.UPDATE_BY_ID)
    public ResponseEntity<ApiResponse> updateReview(
            @PathVariable Long id, @RequestBody RequestUpdateReview request) {
        try {
            Review review = serviceReview.updateReview(id, request);
            return ResponseEntity
                    .ok(new ApiResponse(FeedbackMessage.RESOURCE_UPDATED,
                            review.getId()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_USER_REVIEWS)
    public ResponseEntity<ApiResponse> getAllReviewsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var reviewPage = serviceReview.getAllReviewsByUserId(userId, page, size);
        return ResponseEntity.status(FOUND)
                .body(new ApiResponse(FeedbackMessage.FOUND, reviewPage));
    }

    @PostMapping(UrlMapping.CREATE)
    public ResponseEntity<ApiResponse> saveReview(
            @RequestParam Long patientId, @RequestParam Long vetId,
                                              @RequestBody Review review) {
        try {
            Review savedReview = serviceReview.saveReview(patientId, vetId, review);
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.CREATED, savedReview.getId()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).
                    body(new ApiResponse(e.getMessage(), null));
        } catch (AlreadyReviewedException e) {
            return ResponseEntity.status(CONFLICT).
                    body(new ApiResponse(e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_VET_STAR_AVG)
    public ResponseEntity<ApiResponse> getAverageRatingForVet(@PathVariable Long vetId) {
        try {
            Double avgStar = serviceReview.getAverageRatingForVet(vetId);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND, avgStar));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_BY_ID)
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long id) {
        try {
            serviceReview.deleteReview(id);
            return ResponseEntity.status(OK)
                    .body(new ApiResponse(FeedbackMessage.RESOURCE_DELETED, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
















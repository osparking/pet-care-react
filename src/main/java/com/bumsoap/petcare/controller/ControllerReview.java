package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.exception.AlreadyReviewedException;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Review;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.review.IServiceReview;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RequiredArgsConstructor
@RequestMapping(UrlMapping.REVIEWS)
@RestController(value = "reviewController")
public class ControllerReview {

    private final IServiceReview serviceReview;

    @PostMapping(UrlMapping.CREATE)
    public ResponseEntity<ApiResponse> saveReview(
            @RequestParam Long patientId, @RequestParam Long vatId,
                                              @RequestBody Review review) {
        try {
            Review savedReview = serviceReview.saveReview(patientId, vatId, review);
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
}

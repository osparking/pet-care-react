package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.VERIFY_TOKEN)
public class ControllerTokenVerif {
    private final IServiceVerifToken serviceVerifToken;
    private final RepositoryUser repositoryUser;

    @GetMapping(UrlMapping.VALIDATE_TOKEN)
    public ResponseEntity<ApiResponse> validateToken(String token) {
        String result = serviceVerifToken.validateToken(token);
        ApiResponse response = switch (result) {
            case FeedbackMessage.INVALID_TOKEN
                    -> new ApiResponse(FeedbackMessage.INVALID_TOKEN, null);
            case FeedbackMessage.VERIFIED_TOKEN
                    -> new ApiResponse(FeedbackMessage.VERIFIED_TOKEN, null);
            case FeedbackMessage.EXPIRED_TOKEN
                    -> new ApiResponse(FeedbackMessage.EXPIRED_TOKEN, null);
            case FeedbackMessage.VALIDATED
                    -> new ApiResponse(FeedbackMessage.VALIDATED, null);
            default
                    -> new ApiResponse(FeedbackMessage.TOKEN_VALI_ERROR, null);
        };
        return ResponseEntity.ok(response);
    }
}

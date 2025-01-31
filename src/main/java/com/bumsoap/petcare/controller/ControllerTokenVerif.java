package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.VERIFY_TOKEN)
public class ControllerTokenVerif {
    private final IServiceVerifToken serviceVerifToken;
    private final RepositoryUser repositoryUser;

    public ResponseEntity<ApiResponse> validateToken(String token) {
        String userId = serviceVerifToken.validateToken(token);
        return null;
    }
}

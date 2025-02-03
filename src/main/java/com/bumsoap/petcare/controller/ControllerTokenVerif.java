package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.ReqTokenVerif;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.VERIFY_TOKEN)
public class ControllerTokenVerif {
    private final IServiceVerifToken serviceVerifToken;
    private final RepositoryUser repositoryUser;

    @GetMapping(UrlMapping.VALIDATE_TOKEN)
    public ResponseEntity<ApiResponse> validateToken(String token) {
        String result = serviceVerifToken.validateToken(token);
        List<String> feedbacks = new ArrayList<>();

        feedbacks.add(FeedbackMessage.NOT_FOUND_VERIF_TOKEN);
        feedbacks.add(FeedbackMessage.VERIFIED_TOKEN);
        feedbacks.add(FeedbackMessage.TOKEN_EXPIRED);
        feedbacks.add(FeedbackMessage.TOKEN_VALIDATED);

        if (!feedbacks.contains(result)) {
            result = FeedbackMessage.TOKEN_VALI_ERROR;
        }
        return ResponseEntity.ok(new ApiResponse(result, null));
    }

    @GetMapping(UrlMapping.TOKEN_EXPIRED)
    public ResponseEntity<ApiResponse> checkIfTokenExpired(String token) {
        boolean isExpired = serviceVerifToken.tokenHasExipred(token);
        ApiResponse response = new ApiResponse(isExpired?
                FeedbackMessage.TOKEN_EXPIRED :
                FeedbackMessage.TOKEN_IS_VALID, null);
        return ResponseEntity.ok(response);
    }


    @PostMapping(UrlMapping.SAVE_TOKEN)
    public ResponseEntity<ApiResponse> saveUserVerifToken(
            @RequestBody ReqTokenVerif request) {
        User user = repositoryUser.findById(request.getUser().getId())
                .orElseThrow(() ->
                        new RuntimeException(FeedbackMessage.NOT_FOUND_USER_ID));
        serviceVerifToken.saveUserVerifToken(request.getToken(), user);
        return ResponseEntity.ok(new ApiResponse(FeedbackMessage.TOKEN_SAVED, null));
    }

    @PutMapping(UrlMapping.GENERATE_NEW_TOKEN)
    public ResponseEntity<ApiResponse> generateNewVerifToken(
            @RequestParam String oldToken) {
        VerifToken newToken = serviceVerifToken.makeNewToken(oldToken);
        return ResponseEntity.ok(new ApiResponse("", newToken));
    }

    @DeleteMapping(UrlMapping.DELETE_TOKEN)
    public ResponseEntity<ApiResponse> deleteUserToken(@RequestParam Long userId) {
        serviceVerifToken.deleteTokenById(userId);
        return ResponseEntity.ok(
                new ApiResponse(FeedbackMessage.DELETED_TOKEN, null));
    }
}

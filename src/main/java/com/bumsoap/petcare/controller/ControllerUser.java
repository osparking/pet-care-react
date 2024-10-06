package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.exception.UserAlreadyExistsException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.user.ServiceUser;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(UrlMapping.USERS)
@RequiredArgsConstructor
public class ControllerUser {
    private final ServiceUser serviceUser;
    private final EntityConverter<User, DtoUser> userConverter;

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest request) {
        try {
            User userSaved = serviceUser.register(request);
            DtoUser userDto = userConverter.mapEntityToDto(userSaved, DtoUser.class);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.SUCCESS, userDto));
        } catch (UserAlreadyExistsException exEx) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(exEx.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }
}

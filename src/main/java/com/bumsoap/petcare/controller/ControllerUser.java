package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.exception.UserAlreadyExistsException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.user.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ControllerUser {
    private final ServiceUser serviceUser;
    private final EntityConverter<User, DtoUser> userConverter;

    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody RegistrationRequest request) {
        try {
            User userSaved = serviceUser.add(request);
            DtoUser userDto = userConverter.mapEntityToDto(userSaved, DtoUser.class);
            return ResponseEntity.ok(new ApiResponse("유저 등록 성공!", userDto));
        } catch (UserAlreadyExistsException exEx) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(exEx.getMessage(), null));
        }
    }
}

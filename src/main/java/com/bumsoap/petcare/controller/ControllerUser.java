package com.bumsoap.petcare.controller;

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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ControllerUser {
    private final ServiceUser serviceUser;

    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody RegistrationRequest request) {
        try {
            User userSaved = serviceUser.add(request);
        } catch (Exception e) {}
        return null;
    }
}

package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.request.LoginRequest;
import com.bumsoap.petcare.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ControllerAuth {
    public ResponseEntity<ApiResponse> login (@Valid LoginRequest request) {

    }
}

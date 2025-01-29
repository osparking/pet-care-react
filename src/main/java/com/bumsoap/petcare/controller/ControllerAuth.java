package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.request.LoginRequest;
import com.bumsoap.petcare.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ControllerAuth {
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<ApiResponse> login (@Valid LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(
                    authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

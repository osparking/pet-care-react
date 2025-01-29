package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.request.LoginRequest;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.response.JwtResponse;
import com.bumsoap.petcare.security.jwt.JwtUtil;
import com.bumsoap.petcare.security.user.PcUserDetails;
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
    private final JwtUtil jwtUtil;

    public ResponseEntity<ApiResponse> login (@Valid LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(
                    authentication);
            String jwt = jwtUtil.generateTokenForUser(authentication);
            PcUserDetails userDetails =
                    (PcUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

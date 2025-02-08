package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.request.LoginRequest;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.response.JwtResponse;
import com.bumsoap.petcare.security.jwt.JwtUtil;
import com.bumsoap.petcare.security.user.PcUserDetails;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.SystemUtils;
import com.bumsoap.petcare.utils.UrlMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping(UrlMapping.AUTH)
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173/")
public class ControllerAuth {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IServiceVerifToken serviceVerifToken;

    @GetMapping(UrlMapping.VERIFY_EMAIL)
    public ResponseEntity<ApiResponse> verifyEmailToken(
            @RequestParam("token") String token) {

        String result =  serviceVerifToken.validateToken(token);
        var response = ResponseEntity.internalServerError()
                .body(new ApiResponse(FeedbackMessage.TOKEN_VALI_ERROR, null));

        if (SystemUtils.isValidateFeedback(result)) {
            HttpStatus status = HttpStatus.OK;
            if (result.equals(FeedbackMessage.TOKEN_EXPIRED) ||
                result.equals(FeedbackMessage.NOT_FOUND_VERIF_TOKEN)) {
                status = HttpStatus.GONE;
            }
            response = ResponseEntity.status(status)
                    .body(new ApiResponse(result, null));
        }
        return response;
    }

    @PostMapping(UrlMapping.LOGIN)
    public ResponseEntity<ApiResponse> login (@Valid @RequestBody LoginRequest request) {
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
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.AUTH_SUCCESS, jwtResponse));
        } catch (DisabledException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(
                    new ApiResponse(FeedbackMessage.DISABLED_USER, null));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(
                    new ApiResponse(e.getMessage(), FeedbackMessage.BAD_CREDENTIAL));
        }
    }
}

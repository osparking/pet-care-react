package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.event.listener.NotiEventListener;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.LoginRequest;
import com.bumsoap.petcare.request.PasswordResetReq;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.response.JwtResponse;
import com.bumsoap.petcare.security.jwt.JwtUtil;
import com.bumsoap.petcare.security.user.PcUserDetails;
import com.bumsoap.petcare.service.password.ServicePwdResetI;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.SystemUtils;
import com.bumsoap.petcare.utils.UrlMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping(UrlMapping.AUTH)
@RequiredArgsConstructor
public class ControllerAuth {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IServiceVerifToken serviceVerifToken;
    private final RepositoryUser repositoryUser;
    private final NotiEventListener notiEventListener;
    private final ServicePwdResetI pwdResetService;

    @PostMapping(UrlMapping.RESEND_EMAIL)
    public ResponseEntity<ApiResponse> resendEmail(@RequestParam String email) {
        try {
            User user = repositoryUser.findByEmail(email).orElseThrow(
                    () -> new UsernameNotFoundException(
                            FeedbackMessage.NOT_FOUND_USER_EMAIL + email));
            notiEventListener.saveToken_sendEmail(user);
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.EMAIL_RESENT, null));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                    new ApiResponse(FeedbackMessage.NOT_FOUND_USER_EMAIL + email,
                            null));
        }
    }

    @GetMapping(UrlMapping.VERIFY_EMAIL)
    public ResponseEntity<ApiResponse> verifyEmailToken(
            @RequestParam("token") String token) {

        StringBuffer emailBuf = new StringBuffer();
        String result =  serviceVerifToken.validateToken(token, emailBuf);
        var response = ResponseEntity.internalServerError()
                .body(new ApiResponse(FeedbackMessage.TOKEN_VALI_ERROR, null));

        if (SystemUtils.isValidateFeedback(result)) {
            HttpStatus status = HttpStatus.OK;
            String data = null;
            if (result.equals(FeedbackMessage.TOKEN_EXPIRED)) {
                status = HttpStatus.GONE;
                data = emailBuf.toString();
            }
            if (result.equals(FeedbackMessage.NOT_FOUND_VERIF_TOKEN)) {
                status = HttpStatus.GONE;
            }
            response = ResponseEntity.status(status)
                    .body(new ApiResponse(result, data));
        }
        return response;
    }

    @PostMapping(UrlMapping.REQUEST_PASSWORD_RESET)
    public ResponseEntity<ApiResponse>
                    handlePasswordResetRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(FeedbackMessage.NO_EMAIL_PWD_RESET_REQ, null));
        }
        try {
            pwdResetService.passwordResetRequest(email);
            return ResponseEntity.ok(new ApiResponse(
                    FeedbackMessage.PWD_RESET_LINK_SENT, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                    new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping(UrlMapping.RESET_PASSWORD)
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody
                                                     PasswordResetReq request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();
        if (token == null || token.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(
                    FeedbackMessage.PWD_RESET_INFO_MISSING, null));
        }
        Optional<User> user = pwdResetService.findUserByResetToken(token);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(FeedbackMessage.INVALID_TOKEN, null));
        }
        User foundUser = user.get();
        String message = pwdResetService.resetPassword(newPassword, foundUser);
        return ResponseEntity.ok(new ApiResponse(message, null));
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

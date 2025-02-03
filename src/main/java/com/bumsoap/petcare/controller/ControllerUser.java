package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.event.UserRegisteredEvent;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.exception.UserAlreadyExistsException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.request.RequestChangePwd;
import com.bumsoap.petcare.request.UserUpdateRequest;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.password.ServicePwdChangeI;
import com.bumsoap.petcare.service.user.ServiceUser;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.USERS)
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173/")
public class ControllerUser {
    private final ServiceUser serviceUser;
    private final EntityConverter<User, DtoUser> userConverter;
    private final ServicePwdChangeI servicePwdChangeI;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;

    @PutMapping(UrlMapping.CHANGE_PASSWORD)
    public ResponseEntity<ApiResponse> changePassword(
            @PathVariable Long userId, @RequestBody RequestChangePwd request) {
        try {
            servicePwdChangeI.changePwd(userId, request);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.UPDATED_PWD, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest request) {
        try {
            String encodedPwd = passwordEncoder.encode(request.getPassword());
            request.setPassword(encodedPwd);
            User userSaved = serviceUser.register(request);
            publisher.publishEvent(new UserRegisteredEvent(userSaved));
            DtoUser userDto = userConverter.mapEntityToDto(userSaved, DtoUser.class);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.CREATED_USER, userDto));
        } catch (UserAlreadyExistsException exEx) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(exEx.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_USER)
    public ResponseEntity<ApiResponse> update(@PathVariable Long userId,
                                              @RequestBody UserUpdateRequest request) {
        try {
            User theUser = serviceUser.update(userId, request);
            DtoUser updatedUser = userConverter.mapEntityToDto(theUser, DtoUser.class);
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.RESOURCE_UPDATED, updatedUser));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.USER_BY_ID)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long userId) {

        try {
            DtoUser userDto = serviceUser.getDtoUserById(userId);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND_USER_BY_ID, userDto));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_USER)
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long userId) {

        try {
            serviceUser.deleteById(userId);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.RESOURCE_DELETED, null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_ALL)
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<DtoUser> theUsers = serviceUser.getAllUsers();
        return ResponseEntity.status(FOUND).body(
                new ApiResponse(FeedbackMessage.FOUND_ALL_USERS, theUsers));
    }

    @GetMapping(UrlMapping.COUNT_BY_TYPE)
    public ResponseEntity<ApiResponse> countByType(@PathVariable String type) {
        long count = serviceUser.countByType(type);
        return ResponseEntity.status(OK).body(
                new ApiResponse(FeedbackMessage.FOUND_USER_COUNT_BY_TYPE, count));
    }

    @GetMapping(UrlMapping.COUNT_BY_MONTH_USER_TYPE)
    public ResponseEntity<ApiResponse> countUsersByMonthAndType() {
        try {
            var userStat = serviceUser.countUsersByMonthAndType();
            return ResponseEntity.status(OK)
                    .body(new ApiResponse(
                            FeedbackMessage.FOUND_USER_COUNT_BY_MO_TYPE, userStat));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.COUNT_BY_ACTIVE_STAT)
    public ResponseEntity<ApiResponse> countUserActiveStatistics() {
        try {
            var userStat = serviceUser.serveUserActiveStatistics();
            return ResponseEntity.ok(new ApiResponse(
                    FeedbackMessage.FOUND_USER_COUNT_BY_ACT_TYPE, userStat));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.COUNT_ALL)
    public long countAllUsers() {
        return serviceUser.countAllUsers();
    }

    @Transactional
    @PutMapping(UrlMapping.TOGGLE_ENABLED)
    public ResponseEntity<ApiResponse> toggleUserEnabledStat(
            @PathVariable Long userId, @PathVariable String flag) {
        try {
            boolean enabled = false;
            if ("true".equalsIgnoreCase(flag)) {
                enabled = true;
            } else if ("false".equalsIgnoreCase(flag)) {
                enabled = false;
            } else {
                throw new RuntimeException("미지의 활성화 값: " + flag);
            }
            int count = serviceUser.updateEnabledStat(enabled, userId);
            if (count == 1) {
                return ResponseEntity.ok(
                        new ApiResponse(FeedbackMessage.RESOURCE_UPDATED, null));
            } else {
                throw new RuntimeException("갱신된 유저 계정 수 불일치");
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_ALL_PATIENTS)
    public ResponseEntity<ApiResponse> getAllPatients() {
        List<DtoUser> theUsers = serviceUser.getAllPatients();
        return ResponseEntity.status(FOUND).body(
                new ApiResponse(FeedbackMessage.FOUND_PATIENTS, theUsers));
    }
}








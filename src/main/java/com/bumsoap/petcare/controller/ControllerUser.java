package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.exception.UserAlreadyExistsException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.request.UserUpdateRequest;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.user.ServiceUser;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.USERS)
@RequiredArgsConstructor
public class ControllerUser {
    private final ServiceUser serviceUser;
    private final EntityConverter<User, DtoUser> userConverter;

    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest request) {
        try {
            User userSaved = serviceUser.register(request);
            DtoUser userDto = userConverter.mapEntityToDto(userSaved, DtoUser.class);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.CREATED, userDto));
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
            return ResponseEntity.status(FOUND).body(new ApiResponse(FeedbackMessage.FOUND, userDto));
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
    public ResponseEntity<ApiResponse> getAllUsers(){
        List<DtoUser> theUsers = serviceUser.getAllUsers();
        return ResponseEntity.status(FOUND).body(
                new ApiResponse(FeedbackMessage.FOUND, theUsers));
    }
}








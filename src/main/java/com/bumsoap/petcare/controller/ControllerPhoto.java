package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.photo.IServicePhoto;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class ControllerPhoto {

    private final IServicePhoto servicePhoto;

    /**
     *
     * @param id the id of the User Photo
     * @param imageData the new image data to update
     * @return
     */
    @PutMapping(UrlMapping.UPDATE_BY_ID)
    public ResponseEntity<ApiResponse> update(@PathVariable Long id,
                                              @RequestBody byte[] imageData) {
        try {
            servicePhoto.update(id, imageData);
            return ResponseEntity.ok()
                    .body(new ApiResponse(FeedbackMessage.RESOURCE_UPDATED, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(FeedbackMessage.NOT_FOUND, null));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(FeedbackMessage.SERVER_ERROR, null));
        }
    }

    @PostMapping(UrlMapping.UPLOAD)
    public ResponseEntity<ApiResponse> upload(@RequestParam MultipartFile file,
                                              @RequestParam Long userId) {
        try {
            servicePhoto.save(userId, file);
            return ResponseEntity.ok()
                    .body(new ApiResponse(FeedbackMessage.CREATED, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(FeedbackMessage.NOT_FOUND, null));
        } catch (IOException | SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(FeedbackMessage.SERVER_ERROR, null));
        }
    }
}

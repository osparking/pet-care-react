package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Photo;
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
@RequestMapping(UrlMapping.PHOTO)
@RequiredArgsConstructor
public class ControllerPhoto {

    private final IServicePhoto servicePhoto;

    @GetMapping(UrlMapping.GET_BY_ID)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        try {
            Photo photo = servicePhoto.findById(id);
            if (photo != null) {
                byte[] photoBytes = servicePhoto.getImageData(photo.getId());
                return ResponseEntity.ok(
                        new ApiResponse(FeedbackMessage.FOUND, photoBytes));
            }
        } catch (ResourceNotFoundException | SQLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(FeedbackMessage.NOT_FOUND, null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(FeedbackMessage.SERVER_ERROR, null));
    }

    @DeleteMapping(UrlMapping.DELETE_BY_ID)
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        try {
            servicePhoto.deleteById(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse(FeedbackMessage.RESOURCE_DELETED, null));
        }  catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(FeedbackMessage.NOT_FOUND, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(FeedbackMessage.SERVER_ERROR, null));
        }
    }

    /**
     *
     * @param id the id of the User Photo
     * @param file the new image file to update
     * @return
     */
    @PutMapping(UrlMapping.UPDATE_BY_ID)
    public ResponseEntity<ApiResponse> update(@PathVariable Long id,
                                              @RequestBody MultipartFile file) {
        try {
            Photo updatedPhoto = servicePhoto.update(id, file);
            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            FeedbackMessage.RESOURCE_UPDATED, updatedPhoto.getId()));
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
            Photo savedPhoto = servicePhoto.save(userId, file);
            return ResponseEntity.ok()
                    .body(new ApiResponse(FeedbackMessage.CREATED,
                            savedPhoto.getId()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(FeedbackMessage.NOT_FOUND, null));
        } catch (IOException | SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(FeedbackMessage.SERVER_ERROR, null));
        }
    }
}

package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Pet;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.pet.IServicePet;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(UrlMapping.PETS)
@RequiredArgsConstructor
public class ControllerPet {
    private final IServicePet servicePet;

    @DeleteMapping(UrlMapping.DELETE_BY_ID)
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        try {
            servicePet.deletePet(id);
            return ResponseEntity
                    .ok(new ApiResponse(FeedbackMessage.DELETED_PET, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_BY_ID)
    public ResponseEntity<ApiResponse> updateById(
            @PathVariable Long id, @RequestBody Pet pet) {
        try {
            Pet petUpdated = servicePet.updatePet(pet, id);
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.UPDATED_PET, petUpdated));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_BY_ID)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        try {
            Pet pet = servicePet.findById(id);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND_PET_BY_ID, pet));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping(UrlMapping.ADD_PETS_FOR_APPOINTMENT)
    public ResponseEntity<ApiResponse> addPetsForAppointment(@RequestBody List<Pet> pets) {
        try {
            List<Pet> addedPets = servicePet.addPetsForAppointment(pets);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.CREATED_PETS, addedPets));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping(UrlMapping.ADD_PET_FOR_APPOINTMENT)
    public ResponseEntity<ApiResponse> addPetForAppointment(@PathVariable Long appointmentId,
                                                            @RequestBody Pet pet) {
        try {
            Pet addedPet = servicePet.addPetForAppointment(appointmentId, pet);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.CREATED_PET, addedPet));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_PET_TYPES)
    public ResponseEntity<ApiResponse> getPetTypes() {
        return ResponseEntity.ok(new ApiResponse(
                FeedbackMessage.FOUND_PET_TYPES, servicePet.getPetTypes()));
    }

    @GetMapping(UrlMapping.GET_PET_COLORS)
    public ResponseEntity<ApiResponse> getPetColors() {
        return ResponseEntity.ok(new ApiResponse(
                FeedbackMessage.FOUND_PET_COLORS, servicePet.getPetColors()));
    }

    @GetMapping(UrlMapping.GET_PET_BREEDS)
    public ResponseEntity<ApiResponse> getPetBreeds(@RequestParam(required = false) String type) {
        return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND_PET_BREEDS, servicePet.getPetBreeds(type)));
    }
}

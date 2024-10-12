package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.model.Pet;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.pet.IServicePet;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.PETS)
@RequiredArgsConstructor
public class ControllerPet {
    private final IServicePet servicePet;

    @PostMapping(UrlMapping.ADD_PETS_FOR_APPOINTMENT)
    public ResponseEntity<ApiResponse> addPetsForAppointment(@RequestBody List<Pet> pets) {
        try {
            List<Pet> addedPets = servicePet.addPetsForAppointment(pets);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.CREATED, addedPets));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}

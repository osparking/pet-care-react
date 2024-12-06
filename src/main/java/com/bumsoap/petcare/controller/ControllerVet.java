package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.vet.IServiceVet;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(UrlMapping.VETS)
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173/")
public class ControllerVet {
    private final IServiceVet serviceVet;

    @GetMapping(UrlMapping.GET_ALL_VETS)
    public ResponseEntity<ApiResponse> getAllVets() {
        List<DtoUser> allVets = serviceVet.getAllVetsWithDetails();
        return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND, allVets));
    }

    @GetMapping(UrlMapping.GET_AVAILABLE_VETS)
    public ResponseEntity<ApiResponse> getVetsForAppointment(
            @RequestParam String specialization,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalTime time) {
        List<DtoUser> allAvailableVets =
                serviceVet.getAvailVetsForAppointment(specialization, date, time);
        if (allAvailableVets.isEmpty()) {
            return  ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(FeedbackMessage.NO_VETS_FOUND, null));
        }
        return ResponseEntity.ok(
                new ApiResponse(FeedbackMessage.FOUND, allAvailableVets));
    }
}

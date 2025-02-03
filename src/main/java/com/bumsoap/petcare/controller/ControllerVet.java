package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
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
        return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND_ALL_VETS, allVets));
    }

    @GetMapping(UrlMapping.GET_ALL_SPECIALIZATIONS)
    public ResponseEntity<ApiResponse> getAllSpecial() {
        return ResponseEntity.ok(new ApiResponse(
                FeedbackMessage.FOUND_ALL_SPECIAL, serviceVet.getSpecialList()));
    }

    @GetMapping(UrlMapping.GET_AVAILABLE_VETS)
    public ResponseEntity<ApiResponse> getVetsForAppointment(
            @RequestParam String specialization,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalTime time) {
        try {
            List<DtoUser> allAvailableVets = serviceVet
                    .getAvailVetsForAppointment(specialization, date, time);
            if (allAvailableVets.isEmpty()) {
                return  ResponseEntity.status(NOT_FOUND).body(
                        new ApiResponse(FeedbackMessage.NOT_FOUND_VET_SPECIAL, null));
            }
            return ResponseEntity.ok(new ApiResponse(
                    FeedbackMessage.FOUND_ALL_AVAIL_VETS, allAvailableVets));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_VET_COUNT_BY_SPECIAL)
    public ResponseEntity<ApiResponse> getVetCountBySpecial(){
        var result = serviceVet.countVetBySpecial();
        return ResponseEntity.ok(new ApiResponse(
                FeedbackMessage.FOUND_VET_COUNTS_BY_SPECIAL, result));
    }
}

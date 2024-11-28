package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.vet.IServiceVet;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.VETS)
@RequiredArgsConstructor
public class ControllerVet {
    private final IServiceVet serviceVet;

    @GetMapping(UrlMapping.GET_ALL_VETS)
    public ResponseEntity<ApiResponse> getAllVets() {
        List<DtoUser> allVets = serviceVet.getAllVetsWithDetails();
        return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND, allVets));
    }
}

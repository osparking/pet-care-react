package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.appointment.ServiceAppointment;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(UrlMapping.APPOINTMENT)
@RequiredArgsConstructor
public class ControllerAppointment {
    private final ServiceAppointment serviceAppointment;

    @GetMapping(UrlMapping.GET_ALL)
    public ResponseEntity getAllAppointments() {
        try {
            return ResponseEntity.status(FOUND)
                    .body(new ApiResponse(FeedbackMessage.FOUND,
                            serviceAppointment.getAllAppointments()));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}

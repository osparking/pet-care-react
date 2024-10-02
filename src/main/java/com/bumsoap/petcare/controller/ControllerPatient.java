package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.model.Patient;
import com.bumsoap.petcare.service.ServicePatient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class ControllerPatient {
    private final ServicePatient servicePatient;

    @PostMapping
    public void add(@RequestBody Patient patient) {
        servicePatient.add(patient);
    }
}

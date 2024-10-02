package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.service.ServiceVet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/veterinarians")
@RequiredArgsConstructor
public class ControllerVet {
    private final ServiceVet serviceVet;

    @PostMapping
    public void add(@RequestBody Veterinarian veterinarian) {
        serviceVet.add(veterinarian);
    }
}

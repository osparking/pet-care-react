package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.service.user.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ControllerUser {
    private final ServiceUser serviceUser;

    @PostMapping
    public User add(@RequestBody RegistrationRequest request) {
        return serviceUser.add(request);
    }
}

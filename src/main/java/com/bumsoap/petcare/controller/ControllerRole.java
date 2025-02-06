package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.service.role.ServiceRoleI;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.ROLES)
public class ControllerRole {
    private final ServiceRoleI roleService;
}

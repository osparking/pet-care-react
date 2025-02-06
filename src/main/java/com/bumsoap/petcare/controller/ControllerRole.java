package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.model.Role;
import com.bumsoap.petcare.service.role.ServiceRoleI;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.ROLES)
public class ControllerRole {
    private final ServiceRoleI roleService;

    @GetMapping(UrlMapping.GET_ALL_ROLES)
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping(UrlMapping.GET_ROLE_BY_ID)
    public Role findRoleById(Long id) {
        return roleService.findById(id);
    }
}

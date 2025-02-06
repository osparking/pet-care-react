package com.bumsoap.petcare.service.role;

import com.bumsoap.petcare.model.Role;

import java.util.List;

public interface ServiceRoleI {
    List<Role> getRoles();
    Role findByName(String name);
    Role findById(Long id);


}

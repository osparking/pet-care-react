package com.bumsoap.petcare.service.role;

import com.bumsoap.petcare.model.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ServiceRoleI {
    List<Role> getRoles();
    Role findByName(String name);
    Role findById(Long id);
    void saveRole(Role role);

    Set<Role> getRoleSet(String userType);
}

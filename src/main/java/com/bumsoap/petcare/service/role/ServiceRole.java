package com.bumsoap.petcare.service.role;

import com.bumsoap.petcare.model.Role;
import com.bumsoap.petcare.repository.RepositoryRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceRole implements ServiceRoleI {
    private final RepositoryRole roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Collection<Role> setUserRoles(List<String> roleNames) {
        return roleNames.stream().map(roleName ->
                roleRepository.findByName("ROLE_" + roleName)).toList();
    }
}

package com.bumsoap.petcare.service.role;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Role;
import com.bumsoap.petcare.repository.RepositoryRole;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Set<Role> getRoleSet(String userType) {
        var roleSet = new HashSet<Role>();

        roleRepository
            .findByName("ROLE_" + userType)
            .ifPresentOrElse(roleSet::add,
                () -> { throw new ResourceNotFoundException(
                                    FeedbackMessage.NOT_FOUND_ROLE);
                }
            );
        return roleSet;
    }
}

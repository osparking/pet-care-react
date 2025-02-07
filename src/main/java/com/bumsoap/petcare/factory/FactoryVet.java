package com.bumsoap.petcare.factory;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.repository.RepositoryVet;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.service.role.ServiceRoleI;
import com.bumsoap.petcare.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactoryVet {
    private final RepositoryVet veterinarianRepository;
    private final UserAttributesMapper userAttributesMapper;
    private final ServiceRoleI roleService;

    public Veterinarian createVeterinarian(RegistrationRequest request) {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setRoles(roleService.getRoleSet("VET"));
        userAttributesMapper.setCommonAttributes(request, veterinarian);
        veterinarian.setSpecialization(request.getSpecialization());
        return veterinarianRepository.save(veterinarian);
    }
}

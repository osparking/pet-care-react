package com.bumsoap.petcare.factory;

import com.bumsoap.petcare.model.Patient;
import com.bumsoap.petcare.model.Role;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryPatient;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.service.role.ServiceRoleI;
import com.bumsoap.petcare.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FactoryPatient {
    private final RepositoryPatient repositoryPatient;
    private final UserAttributesMapper userAttributesMapper;
    private final ServiceRoleI roleService;

    public Patient createPatient(RegistrationRequest request) {
        Role patientRole = roleService.findByName("ROLE_PATIENT");
        Patient patient = new Patient();
        patient.setRoles(Set.of(patientRole));
        userAttributesMapper.setCommonAttributes(request, patient);
        return repositoryPatient.save(patient);
    }
}

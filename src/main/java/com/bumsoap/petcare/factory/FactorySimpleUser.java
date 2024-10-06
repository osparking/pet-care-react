package com.bumsoap.petcare.factory;

import com.bumsoap.petcare.exception.UserAlreadyExistsException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FactorySimpleUser implements FactoryUser{
    private final RepositoryUser userRepository;
    private final FactoryVet veterinarianFactory;
    private final FactoryPatient patientFactory;
    private final FactoryAdmin adminFactory;

    @Override
    public User register(RegistrationRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("오류 - 등록된 이메일 : "
                    + registrationRequest.getEmail());
        }
        switch (registrationRequest.getUserType()) {
            case "VET" ->{return veterinarianFactory.createVeterinarian(registrationRequest);
            }
            case "PATIENT" -> { return  patientFactory.createPatient(registrationRequest);
            }
            case "ADMIN" -> {return adminFactory.createAdmin(registrationRequest);
            }
            default -> {
                return null;
            }
        }
    }
}

package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.factory.FactoryUser;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUser {
    private final FactoryUser userFactory;

    public User add(RegistrationRequest request) {
        return userFactory.createUser(request);
    }
}

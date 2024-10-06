package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.factory.FactoryUser;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUser implements IServiceUser {
    private final FactoryUser userFactory;

    @Override
    public User register(RegistrationRequest request) {
        return userFactory.register(request);
    }
}

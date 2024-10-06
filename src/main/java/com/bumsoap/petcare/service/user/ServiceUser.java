package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.factory.FactoryUser;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.request.UserUpdateRequest;
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

    public User update(UserUpdateRequest request) {
        return null; // update logic here
    }
}

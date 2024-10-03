package com.bumsoap.petcare.factory;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;

public class FactorySimpleUser implements FactoryUser{
    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        return null;
    }
}

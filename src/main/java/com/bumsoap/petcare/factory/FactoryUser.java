package com.bumsoap.petcare.factory;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;

public interface FactoryUser {
    public User createUser(RegistrationRequest registrationRequest);
}

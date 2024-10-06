package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;

public interface IServiceUser {
    User register(RegistrationRequest request);
}

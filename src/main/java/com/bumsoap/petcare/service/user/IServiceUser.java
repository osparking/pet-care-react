package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.request.UserUpdateRequest;

public interface IServiceUser {
    User register(RegistrationRequest request);

    User update(Long userId, UserUpdateRequest request);

    void deleteById(Long userId);

    User findById(Long userId);
}

package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.request.UserUpdateRequest;

import java.sql.SQLException;
import java.util.List;

public interface IServiceUser {
    User register(RegistrationRequest request);

    User update(Long userId, UserUpdateRequest request);

    void deleteById(Long userId);

    User findById(Long userId);

    List<DtoUser> getAllUsers();

    DtoUser getDtoUserById(Long userId) throws SQLException;
}

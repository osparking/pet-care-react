package com.bumsoap.petcare.service.token;

import com.bumsoap.petcare.model.User;

public interface IServiceVerifToken {
    String validateToken(String token);
    void saveUserVerifToken(String token, User user);

}

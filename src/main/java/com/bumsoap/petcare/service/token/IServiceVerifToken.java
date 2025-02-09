package com.bumsoap.petcare.service.token;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;

import java.util.Optional;

public interface IServiceVerifToken {
    String validateToken(String token);
    void saveUserVerifToken(String token, User user);
    VerifToken makeNewToken(String oldToken);
    Optional<VerifToken> findByToken(String token);
    void deleteTokenById(Long id);
    boolean tokenHasExipred(String token);
}

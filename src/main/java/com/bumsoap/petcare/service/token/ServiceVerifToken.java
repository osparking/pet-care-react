package com.bumsoap.petcare.service.token;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;

import java.util.Optional;

public class ServiceVerifToken implements  IServiceVerifToken{
    @Override
    public String validateToken(String token) {
        return "";
    }

    @Override
    public void saveUserVerifToken(String token, User user) {

    }

    @Override
    public VerifToken makeNewToken(String oldToken) {
        return null;
    }

    @Override
    public Optional<VerifToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void deleteTokenById(Long id) {

    }

    @Override
    public boolean tokenHasExipred(String token) {
        return false;
    }
}

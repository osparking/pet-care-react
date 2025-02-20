package com.bumsoap.petcare.service.password;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.repository.RepositoryVerifToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicePwdReset implements  ServicePwdResetI{
    private final RepositoryVerifToken verifTokenRepository;

    @Override
    public Optional<User> findUserByResetToken(String token) {
        return Optional.empty();
    }

    @Override
    public void passwordResetRequest(String email) {

    }

    @Override
    public String resetPassword(String password, User user) {
        return "";
    }
}

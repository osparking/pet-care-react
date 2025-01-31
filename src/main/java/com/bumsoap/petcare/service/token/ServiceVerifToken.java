package com.bumsoap.petcare.service.token;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.repository.RepositoryVerifToken;
import com.bumsoap.petcare.utils.SystemUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceVerifToken implements  IServiceVerifToken{
    private final RepositoryUser userRepository;
    private final RepositoryVerifToken tokenRepository;

    @Override
    public String validateToken(String token) {
        Optional<VerifToken> optionalVeriTok = findByToken(token);
        if (optionalVeriTok.isEmpty()) {
            return "INVALID";
        }
        User user = optionalVeriTok.get().getUser();
        if (user.getEnabled()) {
            return "VERIFIED";
        }
        if (tokenHasExipred(token)) {
            return "EXPIRED";
        }
        user.setEnabled(true);
        userRepository.save(user);

        return "VALID";
    }

    @Override
    public void saveUserVerifToken(String token, User user) {
        var verifToken = new VerifToken(token, user);
        tokenRepository.save(verifToken);
    }

    @Override
    public VerifToken makeNewToken(String oldToken) {
        var optionalToken = findByToken(oldToken);
        if (optionalToken.isPresent()) {
            VerifToken verifToken = optionalToken.get();
            verifToken.setToken(UUID.randomUUID().toString());
            verifToken.setExpireDate(SystemUtils.getExpireTime());

            return tokenRepository.save(verifToken);
        }
        throw new IllegalStateException("잘못된 토큰: " + oldToken);
    }

    @Override
    public Optional<VerifToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteTokenById(Long id) {
        tokenRepository.deleteById(id);
    }

    @Override
    public boolean tokenHasExipred(String token) {
        return false;
    }
}

package com.bumsoap.petcare.service.token;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.repository.RepositoryVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
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
    public String validateToken(String token, StringBuffer emailBuf) {
        Optional<VerifToken> optionalVeriTok = findByToken(token);
        if (optionalVeriTok.isEmpty()) {
            return FeedbackMessage.NOT_FOUND_VERIF_TOKEN;
        }
        User user = optionalVeriTok.get().getUser();
        if (user.getEnabled()) {
            return FeedbackMessage.VERIFIED_TOKEN;
        }
        if (tokenHasExipred(token)) {
            emailBuf.append(user.getEmail());
            return FeedbackMessage.TOKEN_EXPIRED;
        }
        user.setEnabled(true);
        userRepository.save(user);

        return FeedbackMessage.TOKEN_VALIDATED;
    }

    @Override
    public Long saveUserVerifToken(String token, User user) {
        var verifToken = new VerifToken(token, user);
        var savedToken = tokenRepository.save(verifToken);
        return savedToken.getId();
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
        var verifToken = tokenRepository.findByToken(token);
        if (verifToken.isEmpty()) {
            return true;
        } else {
            var expireDate = verifToken.get().getExpireDate();
            return System.currentTimeMillis() > expireDate.getTime();
        }
    }

    @Override
    public void deleteVeToken(Long vtId, Long id) {
        tokenRepository.deleteVeToken(vtId, id);
    }
}

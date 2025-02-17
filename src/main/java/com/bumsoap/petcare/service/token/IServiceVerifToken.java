package com.bumsoap.petcare.service.token;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;

import java.util.Optional;

public interface IServiceVerifToken {
    String validateToken(String token, StringBuffer emailBuf);

    /**
     * 한 유저에게 할당된 토큰을 소멸 시점과 함께 DB 에 저장한다.
     *
     * @param token
     * @param user
     * @return
     */
    Long saveUserVerifToken(String token, User user);
    VerifToken makeNewToken(String oldToken);
    Optional<VerifToken> findByToken(String token);
    void deleteTokenById(Long id);
    boolean tokenHasExipred(String token);
}

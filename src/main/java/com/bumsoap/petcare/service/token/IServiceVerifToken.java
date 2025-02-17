package com.bumsoap.petcare.service.token;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 유저에게 발급된 토큰 중, 최근 검증토큰과 ID가 다른 토큰(들)을 삭제한다.
     * @param vtId 최근 발급된 검증 토큰 ID
     * @param userId 토큰을 발급받은 유저 ID
     */
    void deleteVeToken(Long vtId, Long userId);
}

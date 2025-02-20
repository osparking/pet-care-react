package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.VerifToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RepositoryVerifToken extends JpaRepository<VerifToken, Long> {
    /**
     * 토큰으로 (유저 D등을 멤버로 가진)검증 토큰 행을 찾음.
     * @param token 탐색 키
     * @return 선택적으로, 검증 토큰 행
     */
    Optional<VerifToken> findByToken(String token);

    @Query(value = "DELETE FROM verif_token WHERE id != :vtId AND user_id = :userId",
            nativeQuery = true)
    void deleteVeToken(@Param("vtId") Long vtId, @Param("userId") Long userId);
}

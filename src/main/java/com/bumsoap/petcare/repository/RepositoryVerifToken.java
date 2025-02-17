package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.VerifToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RepositoryVerifToken extends JpaRepository<VerifToken, Long> {
    Optional<VerifToken> findByToken(String token);

    @Query(value =
            "DELETE FROM verif_token WHERE id != :vtId AND user_id = :userId")
    void deleteVeToken(@Param("vtId") Long vtId, @Param("userId") Long userId);
}

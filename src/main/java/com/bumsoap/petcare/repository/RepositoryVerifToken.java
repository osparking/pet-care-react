package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.VerifToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryVerifToken extends JpaRepository<VerifToken, Long> {
    Optional<VerifToken> findByToken(String token);
}

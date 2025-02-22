package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.PwdResetReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoPwdResetToken extends JpaRepository<PwdResetReq, Long> {
    Optional<PwdResetReq> findByToken(String token);
}

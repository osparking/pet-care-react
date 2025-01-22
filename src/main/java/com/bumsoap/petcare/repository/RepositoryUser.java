package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryUser extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<Veterinarian> findAllByUserType(String vet);

    long countByUserType(String type);

    @Modifying
    @Query("UPDATE User u SET u.enabled = :flag WHERE u.id = :userId")
    int updateEnabledStat(
            @Param("flag") boolean flag, @Param("userId") Long userId);
}

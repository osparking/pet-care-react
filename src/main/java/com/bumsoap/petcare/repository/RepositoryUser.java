package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}

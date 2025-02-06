package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRole extends JpaRepository<Role, Long> {
}

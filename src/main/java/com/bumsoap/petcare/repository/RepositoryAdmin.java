package com.bumsoap.petcare.repository;

import com.bumsoap.petcare.model.Admin;
import com.bumsoap.petcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAdmin extends JpaRepository<Admin, Long> {
}

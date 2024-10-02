package com.bumsoap.petcare.service;

import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.repository.RepositoryVet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceVet {
    private final RepositoryVet repositoryVet;

    public void add(Veterinarian veterinarian) {
        repositoryVet.save(veterinarian);
    }
}

package com.bumsoap.petcare.service;

import com.bumsoap.petcare.model.Patient;
import com.bumsoap.petcare.repository.RepositoryPatient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicePatient {
    private final RepositoryPatient repositoryPatient;

    public void add(Patient patient) {
        repositoryPatient.save(patient);
    }
}

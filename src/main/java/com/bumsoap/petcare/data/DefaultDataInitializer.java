package com.bumsoap.petcare.data;

import com.bumsoap.petcare.repository.RepositoryPatient;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.repository.RepositoryVet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultDataInitializer
        implements ApplicationListener<ApplicationReadyEvent> {
    private final RepositoryUser userRepository;
    private final RepositoryVet veterinarianRepository;
    private final RepositoryPatient patientRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

    }
}

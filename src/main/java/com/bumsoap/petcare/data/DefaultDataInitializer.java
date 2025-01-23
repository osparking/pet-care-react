package com.bumsoap.petcare.data;

import com.bumsoap.petcare.model.Patient;
import com.bumsoap.petcare.model.Veterinarian;
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
        createDefaultVetIfNotExits();
        createDefaultPatientIfNotExits();
    }

    private void createDefaultPatientIfNotExits(){
        for (int i = 1; i<=10; i++){
            String defaultEmail = "pat"+i+"@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            Patient pat = new Patient();
            pat.setFirstName("강호" + i);
            pat.setLastName("한");
            pat.setGender("female");
            pat.setMobile("01045678908");
            pat.setEmail(defaultEmail);
            pat.setPassword("password" + i);
            pat.setUserType("PATIENT");
            Patient thePatient = patientRepository.save(pat);
            thePatient.setEnabled(true);
            System.out.println("제 " + i + " 팻 주인이 만들어 졌습니다.");
        }
    }

    private void createDefaultVetIfNotExits() {
        for (int i = 1; i <= 10; i++) {
            String defaultEmail = "vet" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            Veterinarian vet = new Veterinarian();
            vet.setLastName("정" + i);
            vet.setFirstName("진심");
            vet.setGender("male");
            vet.setMobile("01094567890");
            vet.setEmail(defaultEmail);
            vet.setPassword("password" + i);
            vet.setUserType("VET");
            vet.setSpecialization("피부과");
            Veterinarian theVet = veterinarianRepository.save(vet);
            theVet.setEnabled(true);
            System.out.println("제 " + i + " 기본 사용자가 만들어 졌습니다.");
        }
    }
}

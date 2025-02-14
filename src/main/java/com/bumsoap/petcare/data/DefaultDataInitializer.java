package com.bumsoap.petcare.data;

import com.bumsoap.petcare.model.Admin;
import com.bumsoap.petcare.model.Patient;
import com.bumsoap.petcare.model.Role;
import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.repository.*;
import com.bumsoap.petcare.service.role.ServiceRoleI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DefaultDataInitializer
        implements ApplicationListener<ApplicationReadyEvent> {
    private final RepositoryUser userRepository;
    private final RepositoryVet veterinarianRepository;
    private final RepositoryPatient patientRepository;
    private final RepositoryRole roleRepository;
    private final RepositoryAdmin adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceRoleI roleService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_PATIENT", "ROLE_VET");
        createDefaultRolesIfNotExits(defaultRoles);
        createAdminIfNotExists();
        createDefaultVetIfNotExits();
        createDefaultPatientIfNotExits();
    }

    private void createDefaultRolesIfNotExits(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }

    private void createAdminIfNotExists() {
        final String defaultEmail = "admin@gmail.com";
        if (userRepository.existsByEmail(defaultEmail)) {
            return;
        }
        Role adminRole = roleService.findByName("ROLE_ADMIN");

        Admin admin = new Admin();
        admin.setLastName("관");
        admin.setFirstName("리자");
        admin.setGender("female");
        admin.setMobile("01012345678");
        admin.setEmail(defaultEmail);
        admin.setEnabled(true);
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setUserType("ADMIN");
        admin.setRoles(Set.of(adminRole));
        Admin theAdmin = adminRepository.save(admin);
        System.out.println("관리자가 만들어 졌습니다.");
    }

    private void createDefaultPatientIfNotExits(){
        Role patientRole = roleService.findByName("ROLE_PATIENT");

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
            pat.setEnabled(true);
            pat.setPassword(passwordEncoder.encode("password" + i));
            pat.setUserType("PATIENT");
            pat.setRoles(Set.of(patientRole));
            Patient thePatient = patientRepository.save(pat);
            System.out.println("제 " + i + " 팻 주인이 만들어 졌습니다.");
        }
    }

    private void createDefaultVetIfNotExits() {
        Role vetRole = roleService.findByName("ROLE_VET");

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
            vet.setEnabled(true);
            vet.setPassword(passwordEncoder.encode("password" + i));
            vet.setUserType("VET");
            vet.setRoles(Set.of(vetRole));
            vet.setSpecialization("피부과");
            Veterinarian theVet = veterinarianRepository.save(vet);
            System.out.println("제 " + i + " 기본 사용자가 만들어 졌습니다.");
        }
    }
}

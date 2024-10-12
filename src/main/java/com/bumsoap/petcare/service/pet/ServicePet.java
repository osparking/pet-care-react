package com.bumsoap.petcare.service.pet;

import com.bumsoap.petcare.model.Pet;
import com.bumsoap.petcare.repository.RepositoryPet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ServicePet implements IServicePet {

    private final RepositoryPet repositoryPet;
    
    @Override
    public List<Pet> addPetsForAppointment(List<Pet> pets) {
        return repositoryPet.saveAll(pets);
    }

    @Override
    public Pet updatePeet(Pet pet) {
        return null;
    }

    @Override
    public void deletePet(Long id) {

    }

    @Override
    public Pet findPetById(Long id) {
        return null;
    }
}

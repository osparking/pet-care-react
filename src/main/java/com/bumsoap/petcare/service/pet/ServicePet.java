package com.bumsoap.petcare.service.pet;

import com.bumsoap.petcare.model.Pet;

import java.util.List;

public class ServicePet implements IServicePet{
    @Override
    public List<Pet> addPetForAppointment(List<Pet> pets) {
        return List.of();
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

package com.bumsoap.petcare.service.pet;

import com.bumsoap.petcare.model.Pet;

import java.util.List;

public interface IServicePet {
    List<Pet> addPetForAppointment(List<Pet> pets);
    Pet updatePeet(Pet pet);
    void deletePet(Long id);
    Pet findPetById(Long id);
}

package com.bumsoap.petcare.service.pet;

import com.bumsoap.petcare.model.Pet;

import java.util.List;

public interface IServicePet {
    List<Pet> addPetsForAppointment(List<Pet> pets);
    Pet updatePet(Pet pet, Long id);
    void deletePet(Long id);
    Pet findById(Long id);

    List<Pet> getPetColors();

    List<Pet> getPetTypess();

    List<Pet> getPetBreeds(String type);
}

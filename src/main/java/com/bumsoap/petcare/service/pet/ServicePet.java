package com.bumsoap.petcare.service.pet;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Pet;
import com.bumsoap.petcare.repository.RepositoryPet;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ServicePet implements IServicePet {

    private final RepositoryPet repositoryPet;

    @Override
    public List<Pet> addPetsForAppointment(List<Pet> pets) {
        return repositoryPet.saveAll(pets);
    }

    @Override
    public Pet updatePet(Pet pet, Long id) {
        Pet existPet = findById(id);
        existPet.setName(pet.getName());
        existPet.setAge(pet.getAge());
        existPet.setType(pet.getType());
        existPet.setBreed(pet.getBreed());
        existPet.setColor(pet.getColor());

        return repositoryPet.save(existPet);
    }

    @Override
    public void deletePet(Long id) {
        repositoryPet.findById(id).ifPresentOrElse(repositoryPet::delete,
                () -> {throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
                });
    }

    @Override
    public Pet findById(Long id) {
        return repositoryPet.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND));
    }

    public List<Pet> getPetColors() {
        return repositoryPet.getDistinctColors();
    }

    public List<Pet> getPetTypess() {
        return repositoryPet.getDistinctTypes();
    }

    public List<Pet> getPetBreeds(String type) {
        return repositoryPet.getDistinctBreedsByType(type);
    }
}

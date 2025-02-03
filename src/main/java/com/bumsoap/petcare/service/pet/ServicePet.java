package com.bumsoap.petcare.service.pet;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Pet;
import com.bumsoap.petcare.repository.RepositoryAppointment;
import com.bumsoap.petcare.repository.RepositoryPet;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ServicePet implements IServicePet {

    private final RepositoryPet repositoryPet;
    private final RepositoryAppointment repositoryAppointment;

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
                () -> {throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_PET_ID);
                });
    }

    @Override
    public Pet findById(Long id) {
        return repositoryPet.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_PET_ID));
    }

    @Override
    public List<String> getPetColors() {
        return repositoryPet.getDistinctColors();
    }

    @Override
    public List<String> getPetTypes() {
        return repositoryPet.getDistinctTypes();
    }

    @Override
    public List<String> getPetBreeds(String type) {
        if (type == null || type == "") {
            return repositoryPet.getDistinctBreeds();
        } else {
            return repositoryPet.getDistinctBreedsByType(type);
        }
    }

    @Override
    public Pet addPetForAppointment(Long appointmentId, Pet pet) {
        var appointment = repositoryAppointment.findById(appointmentId)
                .orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_APPOINT_ID));
        pet.setAppointment(appointment);
        return repositoryPet.save(pet);
    }
}

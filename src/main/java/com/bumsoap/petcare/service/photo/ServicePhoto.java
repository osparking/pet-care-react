package com.bumsoap.petcare.service.photo;

import com.bumsoap.petcare.model.Photo;
import com.bumsoap.petcare.repository.RepositoryPhoto;
import com.bumsoap.petcare.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicePhoto implements IServicePhoto  {

    private final RepositoryPhoto repositoryPhoto; // Assuming RepositoryPhoto is a Spring Data JPA Repository for Photo entity
    private final RepositoryUser repositoryUser; // Assuming RepositoryUser is a Spring Data JPA
    @Override
    public Photo save(Long userId, MultipartFile photo) {
        return null;
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Photo update(Long id, byte[] imageData) {
        return null;
    }

    @Override
    public byte[] getImageData(Long id) {
        return new byte[0];
    } // ServicePhoto class extends IServicePhoto interface   {
}

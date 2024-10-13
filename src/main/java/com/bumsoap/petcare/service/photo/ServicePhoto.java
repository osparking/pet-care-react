package com.bumsoap.petcare.service.photo;

import com.bumsoap.petcare.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class ServicePhoto implements IServicePhoto  {
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

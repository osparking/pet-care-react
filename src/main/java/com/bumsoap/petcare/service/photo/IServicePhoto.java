package com.bumsoap.petcare.service.photo;

import com.bumsoap.petcare.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IServicePhoto {
    Photo save(Long userId, MultipartFile photo);
    Optional<Photo> findById(Long id);
    void deleteById(Long id);
}

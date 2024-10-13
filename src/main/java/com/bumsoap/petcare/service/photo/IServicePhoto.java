package com.bumsoap.petcare.service.photo;

import com.bumsoap.petcare.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface IServicePhoto {
    Photo save(Long userId, MultipartFile photo) throws IOException, SQLException;
    Optional<Photo> findById(Long id);
    void deleteById(Long id);
    Photo update(Long id, byte[] imageData);
    byte[] getImageData(Long id);
}

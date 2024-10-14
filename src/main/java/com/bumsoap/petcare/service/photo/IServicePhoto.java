package com.bumsoap.petcare.service.photo;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface IServicePhoto {
    Photo save(Long userId, MultipartFile photo) throws IOException, SQLException;
    Photo findById(Long id);
    void deleteById(Long id);

    Photo update(Long id, MultipartFile file)
            throws SQLException, ResourceNotFoundException;

    byte[] getImageData(Long id) throws SQLException;
}

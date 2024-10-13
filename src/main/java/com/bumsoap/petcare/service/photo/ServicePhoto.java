package com.bumsoap.petcare.service.photo;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Photo;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryPhoto;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicePhoto implements IServicePhoto  {

    private final RepositoryPhoto repositoryPhoto; // Assuming RepositoryPhoto is a Spring Data JPA Repository for Photo entity
    private final RepositoryUser repositoryUser; // Assuming RepositoryUser is a Spring Data JPA
    @Override
    public Photo save(Long userId, MultipartFile file)
            throws IOException, SQLException {
        Photo photo = new Photo();

        if (file != null && !file.isEmpty()) {
            photo.setImage(new SerialBlob(file.getBytes()));
            photo.setFileType(file.getContentType());
        }
        Photo savedPhoto = repositoryPhoto.save(photo);

        Optional<User> theUser = repositoryUser.findById(userId);
        theUser.ifPresentOrElse(user -> { user.setPhoto(savedPhoto);},
                ()-> {
            throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
        });
        repositoryUser.save(theUser.get());
        return savedPhoto;
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return repositoryPhoto.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repositoryPhoto.findById(id)
                .ifPresentOrElse(repositoryPhoto::delete,
                        () -> {
                    throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
                });
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

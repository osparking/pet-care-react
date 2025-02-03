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
            photo.setFileName(file.getOriginalFilename());
        }
        Photo savedPhoto = repositoryPhoto.save(photo);

        Optional<User> theUser = repositoryUser.findById(userId);
        theUser.ifPresentOrElse(user -> { user.setPhoto(savedPhoto);},
                ()-> {
            throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_USER_ID);
        });
        repositoryUser.save(theUser.get());
        return savedPhoto;
    }

    @Override
    public Photo findById(Long id) throws ResourceNotFoundException {
        return repositoryPhoto.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_PHOTO_ID));
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException{
        repositoryPhoto.findById(id)
                .ifPresentOrElse(repositoryPhoto::delete,
                        () -> {
                    throw new ResourceNotFoundException(
                            FeedbackMessage.NOT_FOUND_PHOTO_ID);
                });
    }

    @Override
    public Photo update(Long id, MultipartFile file)
            throws SQLException, ResourceNotFoundException {
        try {
            Photo photo = findById(id);
            if (photo != null) {
                photo.setFileType(file.getContentType());
                if (file!= null &&!file.isEmpty()) {
                    photo.setImage(new SerialBlob(file.getBytes()));
                    photo.setFileName(file.getOriginalFilename());
                }
            }
            return repositoryPhoto.save(photo);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getImageData(Long id)
            throws SQLException, ResourceNotFoundException{
        try {
            Photo thePhoto = findById(id);

            if (thePhoto != null) {
                return thePhoto.getImage()
                        .getBytes(1, (int) thePhoto.getImage().length());
            }
            return new byte[0];
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }
}

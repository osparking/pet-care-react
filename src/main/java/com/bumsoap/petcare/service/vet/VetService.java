package com.bumsoap.petcare.service.vet;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.repository.IRepositoryReview;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.repository.RepositoryVet;
import com.bumsoap.petcare.service.photo.IServicePhoto;
import com.bumsoap.petcare.service.review.IServiceReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VetService implements IVetService {
    private final EntityConverter<Veterinarian, DtoUser> entityConverter;
    private final IServiceReview serviceReview;
    private final IRepositoryReview repositoryReview;
    private final IServicePhoto servicePhoto;
    private final RepositoryUser repositoryUser;

    @Override
    public List<DtoUser> getAllVetsWithDetails() {
        List<Veterinarian> veterinarians = repositoryUser.findAllByUserType("VET");
        return veterinarians.stream()
               .map(this::mapVeterinarianToDtoUser)
               .toList();
    }

    private DtoUser mapVeterinarianToDtoUser(Veterinarian veterinarian) {
        DtoUser dtoUser = entityConverter.mapEntityToDto(veterinarian, DtoUser.class);
        double ratingAvg = serviceReview.getAverageRatingForVet(veterinarian.getId());
        Long reviewerTotal = repositoryReview.countByVeterinarianId(veterinarian.getId());
        dtoUser.setAverageRating(ratingAvg);
        dtoUser.setTotalReviewers(reviewerTotal);
        if (veterinarian.getPhoto() != null) {
            try {
                byte[] photoBytes = servicePhoto.getImageData(veterinarian.getPhoto().getId());
                dtoUser.setPhoto(photoBytes);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return dtoUser;
    }
}

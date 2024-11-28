package com.bumsoap.petcare.service.vet;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.repository.IRepositoryReview;
import com.bumsoap.petcare.repository.RepositoryVet;
import com.bumsoap.petcare.service.review.IServiceReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VetService {
    private final RepositoryVet repositoryVet;
    private final EntityConverter<Veterinarian, DtoUser> entityConverter;
    private final IServiceReview serviceReview;
    private final IRepositoryReview repositoryReview;

    private DtoUser mapVeterinarianToDtoUser(Veterinarian veterinarian) {
        DtoUser dtoUser = entityConverter.mapEntityToDto(veterinarian, DtoUser.class);
        double ratingAvg = serviceReview.getAverageRatingForVet(veterinarian.getId());
        Long reviewerTotal = repositoryReview.countByVeterinarianId(veterinarian.getId());
        dtoUser.setAverageRating(ratingAvg);
        dtoUser.setTotalReviewers(reviewerTotal);
        return dtoUser;
    }
}

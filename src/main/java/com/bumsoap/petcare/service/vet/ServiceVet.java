package com.bumsoap.petcare.service.vet;

import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.model.Veterinarian;
import com.bumsoap.petcare.repository.IRepositoryReview;
import com.bumsoap.petcare.repository.RepositoryAppointment;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.repository.RepositoryVet;
import com.bumsoap.petcare.service.photo.IServicePhoto;
import com.bumsoap.petcare.service.review.IServiceReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceVet implements IServiceVet {
    private final EntityConverter<Veterinarian, DtoUser> entityConverter;
    private final IServiceReview serviceReview;
    private final IRepositoryReview repositoryReview;
    private final IServicePhoto servicePhoto;
    private final RepositoryUser repositoryUser;
    private final RepositoryAppointment repositoryAppointment;
    private final RepositoryVet repositoryVet;

    @Override
    public List<String> getSpecialList() {
        return repositoryVet.getDistinctSpecial();
    }

    @Override
    public List<DtoUser> getAllVetsWithDetails() {
        List<Veterinarian> veterinarians = repositoryUser.findAllByUserType("VET");
        return veterinarians.stream()
               .map(this::mapVeterinarianToDtoUser)
               .toList();
    }

//        LocalTime exStart = existingAppo.getTime(); // 기존 시작: 오후 2시
//        LocalTime exStop = exStart.plusHours(2); // 기존 종료: 오후 4시
//        LocalTime exMarginStart = exStart.minusHours(1); // 기존 시작 앞 1시간 마진: 오후 1시
//        LocalTime exMarginStop = exStop.plusHours(3); // 기존 종료 후 1시간 마진: 오후
//        return !(tryEnd.compareTo(exMarginStart) <= 0 || tryStart.compareTo(exMarginStop) >= 0);
    private boolean apposOverlap(Appointment existingAppointment,
                                 LocalTime requestedStartTime,
                                 LocalTime requestedEndTime) {
        LocalTime existingStartTime = existingAppointment.getTime();
        LocalTime existingEndTime = existingStartTime.plusHours(2);
        LocalTime unavailableStartTime = existingStartTime.minusHours(1);
        LocalTime unavailableEndTime = existingEndTime.plusMinutes(170);
        return !requestedStartTime.isBefore(unavailableStartTime) &&
                !requestedEndTime.isAfter(unavailableEndTime);
    }

    public List<Veterinarian> getVeterinarianBySpeciality(String speciality) {
        if (!repositoryVet.existsBySpecialization(speciality)) {
            throw new ResourceNotFoundException(
                    "수의사가 발견되지 않은 전문분야: " + speciality);
        }
        return repositoryVet.findVeterinarianBySpecialization(speciality);
    }

    @Override
    public List<DtoUser> getAvailVetsForAppointment(String speciality,
                                                    LocalDate date, LocalTime time) {
        List<Veterinarian> vets = getVetOfSpeciality(speciality, date, time);
        return vets.stream().map(this::mapVeterinarianToDtoUser).toList();
    }

    private List<Veterinarian> getVetOfSpeciality(String special,
                                                  LocalDate date, LocalTime time) {
        List<Veterinarian> vets = getVeterinarianBySpeciality(special);
        return vets.stream()
               .filter(vet -> isVetAvailable(vet, date, time))
               .collect(Collectors.toList());
    }

    private boolean isVetAvailable(Veterinarian vet, LocalDate reqDate, LocalTime reqTime) {
        if (reqDate != null && reqTime != null) {
            LocalTime reqEndTime = reqTime.plusHours(2);
            return repositoryAppointment.findByVeterinarianAndDate(vet, reqDate)
                    .stream().noneMatch(existingAppointment ->
                            apposOverlap(existingAppointment, reqTime, reqEndTime));
        }
        return true;
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

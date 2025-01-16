package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.dto.DtoReview;
import com.bumsoap.petcare.dto.DtoUser;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.factory.FactoryUser;
import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.model.Review;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.IRepositoryReview;
import com.bumsoap.petcare.repository.RepositoryAppointment;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.request.UserUpdateRequest;
import com.bumsoap.petcare.service.appointment.ServiceAppointment;
import com.bumsoap.petcare.service.photo.IServicePhoto;
import com.bumsoap.petcare.service.review.IServiceReview;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceUser implements IServiceUser {
    private final FactoryUser userFactory;
    private final RepositoryUser repositoryUser;
    private final EntityConverter<User, DtoUser> entityConverter;
    private final ServiceAppointment serviceAppointment;
    private final IServicePhoto servicePhoto;
    private final IServiceReview serviceReview;
    private final IRepositoryReview repositoryReview;
    private final RepositoryAppointment repositoryAppointment;

    @Override
    public User register(RegistrationRequest request) {
        return userFactory.register(request);
    }

    @Override
    public User update(Long userId, UserUpdateRequest request) {
        User user = findById(userId);

        user.setMobile(request.getMobile());
        user.setGender(request.getGender());
        user.setSpecialization(request.getSpecialization());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return repositoryUser.save(user);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(Long userId) {
        repositoryUser.findById(userId).ifPresentOrElse(
                user2del -> {
                    List<Review> reviews = repositoryReview.findAllByUserId(userId);
                    repositoryReview.deleteAll(reviews);
                    List<Appointment> appointments =
                            repositoryAppointment.findAllByUserId(userId);
                    repositoryAppointment.deleteAll(appointments);
                    repositoryUser.delete(user2del);
                },
                () -> {
                    throw new ResourceNotFoundException(FeedbackMessage.NOT_FOUND);
                });
    }

    @Override
    public User findById(Long userId) {
        return repositoryUser.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND));
    }

    @Override
    public List<DtoUser> getAllUsers() {
        return repositoryUser.findAll().stream().map
                        (user -> entityConverter.mapEntityToDto(user, DtoUser.class))
                .collect(Collectors.toList());
    }

    @Override
    public DtoUser getDtoUserById(Long userId) throws SQLException {
        // 1. Find user by id
        User user = findById(userId); // Throw exception if user not found

        // 2. Convert user to DtoUser
        DtoUser dtoUser = entityConverter.mapEntityToDto(user, DtoUser.class);

        // 3. Get appointments for user(as patient or veterinarian)
        var dtoAppos = serviceAppointment.getAllDtoAppointmentsByUserId(userId);
        dtoUser.setAppointments(dtoAppos);

        // 4. Get photo image data for the user
        if (user.getPhoto() != null) {
            long photoId = user.getPhoto().getId();

            dtoUser.setPhotoId(photoId);
            dtoUser.setPhoto(servicePhoto.getImageData(photoId));
        }

        // 5. Get reviews for user(as patient or veterinarian)
        setUserReviews(dtoUser, userId);

        return dtoUser;
    }

    @SneakyThrows
    private void setUserReviews(DtoUser dtoUser, Long userId) {
        var reviews = serviceReview.getAllReviewsByUserId(userId, 0, Integer.MAX_VALUE);
        List<DtoReview> dtoReviews = reviews.getContent().stream()
                .map(this::mapToDtoReview).toList();
        if (!dtoReviews.isEmpty() && "VET".equals(dtoUser.getUserType())) {
            double averageRating = serviceReview.getAverageRatingForVet(userId);
            dtoUser.setAverageRating(averageRating);
        }
        dtoUser.setReviews(dtoReviews);
        dtoUser.setTotalReviewers((long) dtoReviews.size());
    }

    private DtoReview mapToDtoReview(Review review) {
        DtoReview dtoReview = new DtoReview();
        dtoReview.setId(review.getId());
        dtoReview.setStars(review.getStars());
        dtoReview.setComment(review.getComment());
        mapVetInfo(dtoReview, review);
        mapPatientInfo(dtoReview, review);
        return dtoReview;
    }

    private void mapPatientInfo(DtoReview dtoReview, Review review) {
        var patient = review.getPatient();
        dtoReview.setPatientId(patient.getId());
        dtoReview.setPatientName(
                patient.getLastName() + ", " + patient.getFirstName());
        if (patient.getPhoto() != null) {
            try {
                dtoReview.setPatientImage(
                        servicePhoto.getImageData(patient.getPhoto().getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            dtoReview.setPatientImage(null);
        }
    }

    private void mapVetInfo(DtoReview dtoReview, Review review) {
        var vet = review.getVeterinarian();
        dtoReview.setVetId(vet.getId());
        dtoReview.setVetName(vet.getLastName() + ", " + vet.getFirstName());
        if (vet.getPhoto() != null) {
            try {
                dtoReview.setVetImage(
                        servicePhoto.getImageData(vet.getPhoto().getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            dtoReview.setVetImage(null);
        }
    }

    @Override
    public long countByType(String type) {
        return repositoryUser.countByUserType(type);
    }

    @Override
    public long countAllUsers() {
        return repositoryUser.count();
    }

}

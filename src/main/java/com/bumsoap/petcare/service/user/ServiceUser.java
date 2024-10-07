package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.factory.FactoryUser;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.request.UserUpdateRequest;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUser implements IServiceUser {
    private final FactoryUser userFactory;
    private final RepositoryUser repositoryUser;

    @Override
    public User register(RegistrationRequest request) {
        return userFactory.register(request);
    }

    @Override
    public User update(Long userId, UserUpdateRequest request) {
        User user = findById(userId);

        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
        user.setSpecialization(request.getSpecialization());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return repositoryUser.save(user);
    }

    @Override
    public User findById(Long userId) {
        return repositoryUser.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_USERID));
    }
}

package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUser {
    private final RepositoryUser repositoryUser;

    public void add(User user) {
        repositoryUser.save(user);
    }
}

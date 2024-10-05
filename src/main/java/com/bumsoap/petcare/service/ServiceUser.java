package com.bumsoap.petcare.service;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUser {
    private final RepositoryUser repositoryUser;

    public User add(User user) {
        return repositoryUser.save(user);
    }
}

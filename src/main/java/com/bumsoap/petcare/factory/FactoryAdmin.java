package com.bumsoap.petcare.factory;

import com.bumsoap.petcare.model.Admin;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryAdmin;
import com.bumsoap.petcare.repository.RepositoryVet;
import com.bumsoap.petcare.request.RegistrationRequest;
import com.bumsoap.petcare.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactoryAdmin {
    private final RepositoryAdmin repositoryAdmin;
    private final UserAttributesMapper userAttributesMapper;

    public User createAdmin(RegistrationRequest request) {
        Admin admin = new Admin();
        userAttributesMapper.setCommonAttributes(request, admin);
        return repositoryAdmin.save(admin);
    }
}

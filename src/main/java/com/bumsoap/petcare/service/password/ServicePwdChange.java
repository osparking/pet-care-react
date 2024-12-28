package com.bumsoap.petcare.service.password;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.RequestChangePwd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicePwdChange implements ServicePwdChangeI {
    private final RepositoryUser repositoryUser;

    public void changePwd(Long userId, RequestChangePwd request) {
        User user = repositoryUser.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("유저 발견 실패"));

        if (request.getCurrentPwd().equals("") || request.getNewPwd().equals("")) {
            throw new IllegalArgumentException("모든 비밀번호 입력 필수");
        }

        if (!request.getCurrentPwd().equals(user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호 불일치");
        }
    }

}

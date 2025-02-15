package com.bumsoap.petcare.service.password;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.RequestChangePwd;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServicePwdChange implements ServicePwdChangeI {
    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePwd(Long userId, RequestChangePwd request) {
        User user = repositoryUser.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("유저 발견 실패"));

        if (request.getCurrentPwd().equals("") || request.getNewPwd().equals("")) {
            throw new IllegalArgumentException("모든 비밀번호 입력 필수");
        }
        boolean matches = passwordEncoder.matches(
                request.getCurrentPwd(), user.getPassword());
        if (!matches) {
            throw new IllegalArgumentException("기존 비밀번호 불일치");
        }

        if (!Objects.equals(request.getNewPwd(), request.getConfirmPwd())) {
            throw new IllegalArgumentException("새 비밀번호의 확인 값 불일치");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPwd()));
        repositoryUser.save(user);
    }

}

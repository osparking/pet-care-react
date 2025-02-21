package com.bumsoap.petcare.service.password;

import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.User;
import java.util.Optional;

public interface ServicePwdResetI {
    Optional<User> findUserByResetToken(String token);
    /**
     * 비밀번호 리셋 요청 대상 유저가 발견되면, 리셋 링크 포함 이메일 전송.
     * @param email
     * @throws ResourceNotFoundException
     */
    void passwordResetRequest(String email);
    String resetPassword(String password, User user);
}

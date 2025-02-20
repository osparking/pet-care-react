package com.bumsoap.petcare.service.password;

import com.bumsoap.petcare.model.User;
import java.util.Optional;

public interface ServicePwdResetI {
    Optional<User> findUserByResetToken(String token);
    void passwordResetRequest(String email);
    String resetPassword(String password, User user);
}

package com.bumsoap.petcare.service.password;

import com.bumsoap.petcare.event.PasswordResetE;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.PwdResetReq;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.model.VerifToken;
import com.bumsoap.petcare.repository.RepoPwdResetToken;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.repository.RepositoryVerifToken;
import com.bumsoap.petcare.service.token.ServiceVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicePwdReset implements  ServicePwdResetI{
    private final RepositoryVerifToken verifTokenRepository;
    private final RepositoryUser userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final ServiceVerifToken verifTokenService;
    private final RepoPwdResetToken pwdResetTokenRepo;

    @Override
    public Optional<User> findUserByResetToken(String token) {
        return verifTokenRepository
                .findByToken(token).map(VerifToken::getUser);
    }

    @Override
    public void passwordResetRequest(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        userOptional.ifPresentOrElse((user) -> {
                var event = new PasswordResetE(this, user);
                eventPublisher.publishEvent(event);
            },
            () -> {
                throw new ResourceNotFoundException(
                    FeedbackMessage.NOT_FOUND_USER_EMAIL + email);
            }
        );
    }

    @Override
    public String resetPassword(String password, User user) {
        try {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return FeedbackMessage.PASSWORD_RESET_SUCCESSFUL;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void pwdResetRequestToken(User user, String token) {
        var prrEntity = new PwdResetReq(token, user);
        pwdResetTokenRepo.save(prrEntity);
    }
}

package com.bumsoap.petcare.security.user;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.utils.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PcUserDetailsService implements UserDetailsService {
    private final RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = repositoryUser.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(FeedbackMessage.NOT_FOUND));
        return PcUserDetails.buildUserDetails(user);
    }
}

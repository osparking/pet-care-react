package com.bumsoap.petcare.security.user;

import com.bumsoap.petcare.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class PcUserDetailsService implements UserDetailsService {
    private final RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return null;
    }
}

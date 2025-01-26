package com.bumsoap.petcare.security.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PcUserDetails {
    private Long id;
    private String email;
    private String password;
    private boolean enabled;

    private Collection<GrantedAuthority> authorities;
}

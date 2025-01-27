package com.bumsoap.petcare.config;

import com.bumsoap.petcare.security.jwt.AuthTokenFilter;
import com.bumsoap.petcare.security.jwt.JwtAuthEntryPt;
import com.bumsoap.petcare.security.user.PcUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {
    private final PcUserDetailsService userDetailsService;
    private final JwtAuthEntryPt authEntryPt;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }
}

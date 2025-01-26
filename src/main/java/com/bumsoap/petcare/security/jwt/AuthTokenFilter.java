package com.bumsoap.petcare.security.jwt;

import com.bumsoap.petcare.security.user.PcUserDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenFilter {
    private JwtUtil jwtUtil;
    PcUserDetails pcUserDetails;
}

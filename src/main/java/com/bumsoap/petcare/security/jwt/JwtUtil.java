package com.bumsoap.petcare.security.jwt;

import com.bumsoap.petcare.security.user.PcUserDetails;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.List;

public class JwtUtil {
    private String jwtSecret;
    private int expirationMs;

    public String generateTokenForUser(Authentication authentication) {
        PcUserDetails userDetails = (PcUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return "";
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}

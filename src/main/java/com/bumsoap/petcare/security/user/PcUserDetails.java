package com.bumsoap.petcare.security.user;

import com.bumsoap.petcare.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PcUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private boolean enabled;

    private Collection<GrantedAuthority> authorities;

    public static PcUserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new PcUserDetails(user.getId(), user.getEmail(),
                user.getPassword(), user.getEnabled(), authorities);
    }

    @Override
    public String getUsername() {
        return email;
    }
}

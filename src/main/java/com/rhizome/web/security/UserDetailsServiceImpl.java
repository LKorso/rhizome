package com.rhizome.web.security;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rhizome.services.implementation.UserService;
import com.rhizome.web.dto.Credentials;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Credentials credentials = userService.getCredentials(email);
        if (isNull(credentials)) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        return new User(
                credentials.getEmail(),
                credentials.getPassword().toLowerCase(),
                true,
                true,
                true,
                true,
                getAuthorities(credentials.getRoles()));
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}

package com.program.web.security;

import com.program.services.UserService;
import com.program.web.dto.Credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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

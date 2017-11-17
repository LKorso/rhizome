package com.program.services;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.program.domain.User;
import com.program.repositories.UserRepository;
import com.program.web.dto.Credentials;
import com.program.web.dto.UserRegistrationDto;
import com.program.web.validation.RegistrationService;

@Service
public class UserService implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User find(String id) {
        return userRepository.findOne(id);
    }

    public List<User> findAll() {
        return toList(userRepository.findAll());
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public boolean registerNewUser(UserRegistrationDto userDto) {
        User newUser = new User();
        newUser.setFirtsName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(userDto.getPassword());
        User savedUser = save(newUser);
        return !isNull(savedUser);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return userRepository.exists(email);
    }

    public Credentials getCredentials(String email) {
        User user = userRepository.findOne(email);
        return isNull(user) ? null :
                new Credentials()
                        .setEmail(user.getEmail())
                        .setPassword(user.getPassword())
                        .setRoles(user.getRoles());
    }

    private <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

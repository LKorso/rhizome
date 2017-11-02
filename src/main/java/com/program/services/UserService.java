package com.program.services;

import com.program.domain.User;
import com.program.repositories.UserRepository;
import com.program.web.dto.UserRegistrationDto;
import com.program.web.security.validation.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

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
    public boolean isEmailRegistred(String email) {
        return !isNull(userRepository.findOne(email));
    }

    private <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

package com.program.services;

import com.program.domain.User;
import com.program.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

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

    private <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

}

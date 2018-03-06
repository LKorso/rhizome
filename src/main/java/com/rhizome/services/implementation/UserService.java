package com.rhizome.services.implementation;

import com.rhizome.domain.User;
import com.rhizome.repositories.UserRepository;
import com.rhizome.services.api.RegistrationService;
import com.rhizome.services.api.dto.UserData;
import com.rhizome.services.mappers.UserDataToUserMapper;
import com.rhizome.web.dto.Credentials;
import com.rhizome.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

@Service
public class UserService implements RegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataToUserMapper userDataToUserMapper;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<UserData> find(String id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return Optional.empty();
        } else {
            return Optional.of(userDataToUserMapper.toUserData(user));
        }
    }

    public List<User> findAll() {
        return toList(userRepository.findAll());
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public boolean registerNewUser(UserRegistrationDto userDto) {
        User newUser = new User();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(userDto.getPassword());
        User savedUser = save(newUser);
        return !isNull(savedUser);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Credentials getCredentials(String email) {
        User user = userRepository.findUserByEmail(email);
        return isNull(user) ? null :
                new Credentials()
                        .setEmail(user.getEmail())
                        .setPassword(user.getPassword())
                        .setRoles(user.getRoles());
    }

    public void update(String email, UserData userData) {
        User user = userRepository.findUserByEmail(email);
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        userRepository.save(user);
    }

    public List<UserData> getUsers(Pageable pageable) {
        return userDataToUserMapper.toUsersData(userRepository.findAll(pageable).getContent());
    }


    private <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

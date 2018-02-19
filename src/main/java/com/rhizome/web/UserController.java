package com.rhizome.web;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhizome.services.implementation.UserService;
import com.rhizome.web.dto.UserRegistrationDto;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void registerNewUser(@Valid UserRegistrationDto userDto) {
        userService.registerNewUser(userDto);
    }

    @GetMapping
    public ResponseEntity userProfile(Principal principal) {
        return userService.find(principal.getName())
                .map(v -> new ResponseEntity(v, OK))
                .orElse(new ResponseEntity(NOT_FOUND));
    }

}

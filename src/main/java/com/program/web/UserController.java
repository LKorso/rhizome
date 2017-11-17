package com.program.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.services.UserService;
import com.program.web.dto.UserRegistrationDto;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public void registerNewUser(@Valid UserRegistrationDto userDto) {
        userService.registerNewUser(userDto);
    }

}

package com.rhizome.web;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rhizome.services.api.dto.UserData;
import com.rhizome.services.implementation.UserService;
import com.rhizome.web.dto.UserRegistrationDto;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public ResponseEntity currentUserProfile(Principal principal) {
        return ResponseEntity.ok(userService.findByEmail(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity userProfile(@PathVariable Integer id) {
        return userService.find(id)
                .map(v -> new ResponseEntity(v, OK))
                .orElse(new ResponseEntity(NOT_FOUND));
    }

    @PostMapping
    public void registerNewUser(@Valid UserRegistrationDto userDto) {
        userService.registerNewUser(userDto);
    }

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void updateUser(UserData userData, Principal principal) {
        userService.update(principal.getName(), userData);
    }

}

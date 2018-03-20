package com.rhizome.web;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rhizome.services.api.dto.UserData;
import com.rhizome.services.implementation.UserService;

@RepositoryRestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/current")
    public ResponseEntity currentUserProfile(Principal principal) {
        return ResponseEntity.ok(userService.findByEmail(principal.getName()));
    }

    @PutMapping("/users")
    @ResponseStatus(NO_CONTENT)
    public void updateUser(UserData userData, Principal principal) {
        userService.update(principal.getName(), userData);
    }

}

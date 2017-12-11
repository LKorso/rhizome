package com.rhizome.web;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhizome.web.dto.Credentials;

@RestController
public class AutorizationController {

    @PostMapping("/login")
    public void login(@Valid Credentials dto) {
    }

}

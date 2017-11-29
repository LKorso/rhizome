package com.rhizome.web;

import com.rhizome.web.dto.Credentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AutorizationController {

    @PostMapping("/login")
    public void login(@Valid Credentials dto) {
    }

}

package com.rhizome.web.dto;

import com.rhizome.web.validation.annotations.EmailValidation;
import com.rhizome.web.validation.annotations.UniqueEmail;
import com.rhizome.web.validation.annotations.PasswordMatches;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@PasswordMatches
public class UserRegistrationDto {

    private String firstName;

    private String lastName;

    @UniqueEmail
    @EmailValidation
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    private String confirmationPassword;

}

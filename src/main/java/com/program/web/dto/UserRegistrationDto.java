package com.program.web.dto;

import com.program.web.security.validation.annotations.EmailValidation;
import com.program.web.security.validation.annotations.NotRegistredEmail;
import com.program.web.security.validation.annotations.PasswordMatches;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@PasswordMatches
public class UserRegistrationDto {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @EmailValidation
    @NotRegistredEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    private String confirmationPassword;

}

package com.program.web.security.validation.validators;

import com.program.web.security.validation.RegistrationService;
import com.program.web.security.validation.annotations.NotRegistredEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NotRegistredEmailValidator implements ConstraintValidator<NotRegistredEmail, String>{

    @Autowired
    private RegistrationService registrationService;

    @Override
    public void initialize(NotRegistredEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return registrationService.isEmailRegistred(email);
    }
}

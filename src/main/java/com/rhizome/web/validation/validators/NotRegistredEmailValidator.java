package com.rhizome.web.validation.validators;

import com.rhizome.services.api.RegistrationService;
import com.rhizome.web.validation.annotations.NotRegistredEmail;
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
        return !registrationService.isEmailRegistered(email);
    }
}

package com.rhizome.web.validation.validators;

import com.rhizome.services.api.RegistrationService;
import com.rhizome.web.validation.annotations.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

    @Autowired
    private RegistrationService registrationService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !registrationService.isEmailRegistered(email);
    }
}

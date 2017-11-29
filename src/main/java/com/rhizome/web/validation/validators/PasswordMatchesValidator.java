package com.rhizome.web.validation.validators;

import com.rhizome.web.validation.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

import static java.util.Objects.isNull;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        PasswordMatches annotation = object.getClass().getDeclaredAnnotation(PasswordMatches.class);
        String passwordField = annotation.passwordField();
        String confirmationField = annotation.confirmationField();
        Object first = getFieldValue(object, passwordField);
        Object second = getFieldValue(object, confirmationField);
        return (!isNull(first) && !isNull(second)) && first.equals(second);
    }

    private Object getFieldValue(Object instance, String fieldName){
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Field " + fieldName + " is not declared in class " + instance.getClass());
        }
    }

}
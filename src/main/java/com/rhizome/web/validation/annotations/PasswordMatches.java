package com.rhizome.web.validation.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rhizome.web.validation.porcessing.FieldName;
import com.rhizome.web.validation.validators.PasswordMatchesValidator;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {

    @FieldName
    String passwordField() default "password";

    @FieldName
    String confirmationField() default "confirmationPassword";

    String message() default "Passwords don't match";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

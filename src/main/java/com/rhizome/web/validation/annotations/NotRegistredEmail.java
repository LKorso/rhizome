package com.rhizome.web.validation.annotations;

import com.rhizome.web.validation.validators.NotRegistredEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Constraint(validatedBy = NotRegistredEmailValidator.class)
public @interface NotRegistredEmail {

    String message() default "Email is already registred!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

package com.rhizome.web.validation.annotations;

import com.rhizome.web.validation.validators.EmailValidator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull
@NotEmpty
@Retention(RUNTIME)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailValidation {

    String message() default "email-not-valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

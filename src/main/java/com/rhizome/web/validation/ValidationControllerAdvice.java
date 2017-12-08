package com.rhizome.web.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.util.Collections.singletonList;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.reflections.Reflections;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rhizome.web.dto.ErrorDto;
import com.rhizome.web.validation.porcessing.FieldName;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class ValidationControllerAdvice {

    private Map<String, List<String>> fieldsByConstraint;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Errors handleValidation(BindException exception) {
        return new Errors(
                exception.getAllErrors()
                        .stream()
                        .map(this::createErrorDto)
                        .collect(Collectors.toList())
        );
    }

    @PostConstruct
    public void init() {
        fieldsByConstraint = new HashMap<>();
        Reflections reflections = new Reflections("com.rhizome.web.validation.annotations");
        reflections.getSubTypesOf(Annotation.class)
                .stream()
                .filter(this::isTargetType)
                .forEach(c -> fieldsByConstraint.put(c.getSimpleName(), getConstraintFields(c)));
    }

    private ErrorDto createErrorDto(ObjectError error) {
        if (error instanceof FieldError) {
            return createErrorDto((FieldError) error);
        } else {
            return new ErrorDto(defineFields(error), error.getDefaultMessage());
        }
    }

    private ErrorDto createErrorDto(FieldError error) {
        return new ErrorDto(singletonList(error.getField()), error.getDefaultMessage());
    }

    private List<String> defineFields(ObjectError error) {
        return fieldsByConstraint.get(error.getCode());
    }

    private boolean isTargetType(Class constraint) {
        Target targets = (Target) constraint.getDeclaredAnnotation(Target.class);
        return Arrays.asList(targets.value()).contains(TYPE);
    }

    private List<String> getConstraintFields(Class constraint) {
        return Arrays.stream(constraint.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(FieldName.class))
                .map(Method::getName) // TODO get value of method not it's name
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private static class Errors {
        private List<ErrorDto> errors;
    }

}

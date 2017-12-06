package com.rhizome.web;

import com.rhizome.web.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@RestControllerAdvice
public class ValidationControllerAdvice {

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Errors handleUserNotFound() {
        return new Errors(singletonList(
                new ErrorDto(Arrays.asList("email", "password"), "Wrong email or password")
        ));
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
        Object[] arguments = error.getArguments();
        return Arrays.asList(arguments)
                .subList(1, arguments.length)
                .stream()
                .map(argument -> ((MessageSourceResolvable) argument).getCodes())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private static class Errors {
        private List<ErrorDto> errors;
    }

}

package com.rhizome.web;

import com.rhizome.web.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public List<ErrorDto> handleValidation(BindException exception) {
        return exception.getAllErrors()
                .stream()
                .map(error -> (FieldError) error)
                .map(error -> new ErrorDto(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

}

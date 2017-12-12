package com.rhizome.web.validation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rhizome.services.api.ErrorCombiner;
import com.rhizome.services.api.dto.ErrorDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class ValidationControllerAdvice {

    @Autowired
    private ErrorCombiner errorCombiner;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Errors handleValidation(BindException exception) {
        return new Errors(
                exception.getAllErrors()
                        .stream()
                        .map(error -> errorCombiner.combine(error.getDefaultMessage()))
                        .collect(Collectors.toList())
        );
    }

    @Data
    @AllArgsConstructor
    private static class Errors {
        private List<ErrorDto> errors;
    }

}

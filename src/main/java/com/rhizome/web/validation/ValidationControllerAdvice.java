package com.rhizome.web.validation;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rhizome.services.api.ErrorDataPreparator;
import com.rhizome.services.api.dto.ErrorsData;

@RestControllerAdvice
public class ValidationControllerAdvice {

    @Autowired
    private ErrorDataPreparator errorDataPreparator;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorsData handleValidation(BindException exception) {
        return errorDataPreparator.combine(
                exception.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

}

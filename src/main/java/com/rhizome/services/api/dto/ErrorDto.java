package com.rhizome.services.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ErrorDto {

    private String message;
    private List<String> fields;

}

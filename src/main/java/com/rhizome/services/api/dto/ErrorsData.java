package com.rhizome.services.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
public class ErrorsData {

    private List<ErrorDto> errors;

    @Data
    @Accessors(chain = true)
    public static class ErrorDto {
        private String message;
        private List<String> fields;
    }

}

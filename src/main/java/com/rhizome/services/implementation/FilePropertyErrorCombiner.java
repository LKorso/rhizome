package com.rhizome.services.implementation;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhizome.services.api.ErrorCombiner;
import com.rhizome.services.api.dto.ErrorsData;

@Component
public class FilePropertyErrorCombiner implements ErrorCombiner {

    private final String ERROR_FIELDS_PATH = "/errors/validation-errors-fields.json";

    @Autowired
    private ObjectMapper jsonMapper;

    @Resource(name = "errorsMessages")
    private Map<String, String> errorsMessages;

    private Map<String, List<String>> errorsFields;

    @Override
    public ErrorsData combine(String errorCode) {
        return new ErrorsData(
                singletonList(createErrorDto(errorCode))
        );
    }

    @Override
    public ErrorsData combine(List<String> errorCodes) {
        return new ErrorsData(errorCodes.stream().map(this::createErrorDto).collect(Collectors.toList()));
    }

    @Override
    public String combineToJson(String errorCode) {
        try {
            return jsonMapper.writeValueAsString(combine(errorCode));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void initErrorFields() {
        try {
            errorsFields = jsonMapper.readValue(getClass().getResourceAsStream(ERROR_FIELDS_PATH), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException("File " + ERROR_FIELDS_PATH + " doesn't exist");
        }
    }

    private ErrorsData.ErrorDto createErrorDto(String errorCode) {
        return new ErrorsData.ErrorDto()
                .setMessage(loadMessage(errorCode))
                .setFields(loadFieldsNames(errorCode));
    }

    private String loadMessage(String errorCode) {
        return errorsMessages.get(errorCode);
    }

    private List<String> loadFieldsNames(String errorCode) {
        return errorsFields.get(errorCode);
    }
}

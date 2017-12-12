package com.rhizome.services.implementation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhizome.services.api.ErrorCombiner;
import com.rhizome.services.api.dto.ErrorDto;

@Component
public class FilePropertyErrorCombiner implements ErrorCombiner {

    private final String ERROR_FIELDS_PATH = "/errors/validation-errors-fields.json";

    @Resource(name = "errorsMessages")
    private Map<String, String> errorsMessages;

    private Map<String, List<String>> errorsFields;

    @Override
    public ErrorDto combine(String errorCode) {
        return new ErrorDto()
                .setMessage(loadMessage(errorCode))
                .setFields(loadFieldsNames(errorCode));
    }

    @PostConstruct
    public void initErrorFields() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            errorsFields = mapper.readValue(getClass().getResourceAsStream(ERROR_FIELDS_PATH), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException("File " + ERROR_FIELDS_PATH +  " doesn't exist");
        }
    }

    private String loadMessage(String errorCode) {
        return errorsMessages.get(errorCode);
    }

    private List<String> loadFieldsNames(String errorCode) {
        return errorsFields.get(errorCode);
    }
}

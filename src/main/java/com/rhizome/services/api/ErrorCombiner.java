package com.rhizome.services.api;

import java.util.List;

import com.rhizome.services.api.dto.ErrorsData;

public interface ErrorCombiner {

    ErrorsData combine(String errorCode);

    ErrorsData combine(List<String> errorCodes);

    String combineToJson(String errorCode);

}

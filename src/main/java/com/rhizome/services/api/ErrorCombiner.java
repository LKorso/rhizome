package com.rhizome.services.api;

import com.rhizome.services.api.dto.ErrorDto;

public interface ErrorCombiner {

    ErrorDto combine(String errorCode);

}

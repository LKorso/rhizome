package com.rhizome.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.rhizome.services.api.ErrorDataPreparator;

@Component
public class AuthenticationExceptionsHandler implements AuthenticationFailureHandler {

    private static final String JSON_TYPE = "application/json";
    private static final String AUTHENTICATION_ERROR_CODE = "not-authenticated";

    @Autowired
    private ErrorDataPreparator errorDataPreparator;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(JSON_TYPE);
        response.getOutputStream().println(getJsonErrors());
    }

    private String getJsonErrors() {
        return errorDataPreparator.combineToJson(AUTHENTICATION_ERROR_CODE);
    }

}

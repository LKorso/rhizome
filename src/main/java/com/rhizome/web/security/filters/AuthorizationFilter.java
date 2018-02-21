package com.rhizome.web.security.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getCookies() != null) {
            Map<String, String> additionalHeaders = prepareAdditionalHeaders(request.getCookies());
            if (!additionalHeaders.isEmpty()) {
                filterChain.doFilter(new CustomHeadersRequest(request, additionalHeaders), response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Map<String, String> prepareAdditionalHeaders(Cookie[] cookies) {
        String accessToken = getAccessToken(cookies);
        Map<String, String> additionalHeaders = new HashMap<>();
        if (accessToken != null) {
            additionalHeaders.put("Authorization", "Bearer " + accessToken);
        }
        return additionalHeaders;
    }

    private String getAccessToken(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

}
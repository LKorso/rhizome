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

public class CookieToHeaderFilter extends OncePerRequestFilter {

    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String AUTHORIZATION_KEY = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String accessToken = getTokenValue(cookies, ACCESS_TOKEN_KEY);
        Map<String, String> additionalHeaders = new HashMap<>();
        if (accessToken != null) {
            additionalHeaders.put(AUTHORIZATION_KEY, "Bearer " + accessToken);
        }
        filterChain.doFilter(new CustomHeadersRequest(request, additionalHeaders), response);
    }

    private String getTokenValue(Cookie[] cookies, String cookieName) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

}

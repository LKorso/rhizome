package com.rhizome.web.security.filters;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomHeadersRequest extends HttpServletRequestWrapper {

    private Map<String, String> additionalHeaders;

    public CustomHeadersRequest(HttpServletRequest request, Map<String, String> additionalHeaders) {
        super(request);
        this.additionalHeaders = additionalHeaders;
    }

    @Override
    public String getHeader(String name) {
        String value = additionalHeaders.get(name);
        return value != null ? value : super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(additionalHeaders.keySet());
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (additionalHeaders.containsKey(name)) {
            values.add(additionalHeaders.get(name));
        }
        return Collections.enumeration(values);
    }

}
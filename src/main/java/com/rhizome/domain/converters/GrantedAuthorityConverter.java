package com.rhizome.domain.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GrantedAuthorityConverter implements Converter<Collection<String>, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Collection<String> source) {
        return source.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructCollectionLikeType(List.class, String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructCollectionLikeType(List.class, GrantedAuthority.class);
    }

}

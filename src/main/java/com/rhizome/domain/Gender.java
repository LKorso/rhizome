package com.rhizome.domain;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Gender {

    MALE("M"),
    FEMALE("F");

    private String value;

    public String getValue() {
        return value;
    }

    public static Gender byValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No geneder was defined for value: " + value));
    }

}

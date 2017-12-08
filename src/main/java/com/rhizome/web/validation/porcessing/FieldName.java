package com.rhizome.web.validation.porcessing;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * Marker, shows if value is field that should be validated
 * For annotations that have {@code @Target} - {@code ElementType.TYPE}
 */
@Retention(RUNTIME)
public @interface FieldName {
}

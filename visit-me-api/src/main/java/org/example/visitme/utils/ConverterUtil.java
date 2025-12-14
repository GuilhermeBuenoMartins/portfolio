package org.example.visitme.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.sqm.sql.internal.InstantiationException;

import tools.jackson.databind.ObjectMapper;

public final class ConverterUtil {

    private ConverterUtil() {
        throw new InstantiationException("This class cannot be instantiated.");
    }

    public static <T> T from(Object object, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, clazz);
    }

    public static <D, T> List<T> from(Collection<D> collection, Class<T> clazz) {
        return collection.stream().map(i -> from(i, clazz)).collect(Collectors.toList());
    }
}
package org.example.visitme.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.sqm.sql.internal.InstantiationException;
import org.springframework.data.domain.Page;

import tools.jackson.databind.ObjectMapper;

public final class ConverterUtil {

    private ConverterUtil() {
        throw new InstantiationException("This class cannot be instantiated.");
    }

    public static <T> T from(final Object object, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, clazz);
    }

    public static <D, T> List<T> from(final Collection<D> collection, Class<T> clazz) {
        return collection.stream().map(i -> from(i, clazz)).collect(Collectors.toList());
    }

    public static <E, T> Page<T> from(final Page<E> page, Class<T> clazz) {
        return page.map(p -> ConverterUtil.from(p, clazz));
    }
}
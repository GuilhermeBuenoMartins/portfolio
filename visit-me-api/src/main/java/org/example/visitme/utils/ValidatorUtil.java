package org.example.visitme.utils;

import java.util.Objects;
import java.util.stream.IntStream;

import org.hibernate.query.sqm.sql.internal.InstantiationException;

public final class ValidatorUtil {

    private ValidatorUtil() {
        throw new InstantiationException("This class cannot be instantiated.");
    }

    public static boolean intValue(Object object, boolean notNull, Integer minimum, Integer maximum) {
        Integer value = object instanceof Integer? (Integer) object: null;
        if (notNull && Objects.isNull(value)) {
            return false;
        } 
        if (Objects.nonNull(minimum) && Objects.nonNull(value)) {
            if (value < minimum) { return false; }
        }
        if (Objects.nonNull(maximum) && Objects.nonNull(value)) {
            if (value > maximum) { return false; }
        }
        return true;
    }

    public static boolean doubleValue(Object object, boolean notNull, Double minimum, Double maximum) {
        Double value = object instanceof Double? (Double) object: null;
        if (notNull && Objects.isNull(value)) {
            return false;
        } 
        if (Objects.nonNull(minimum) && Objects.nonNull(value)) {
            if (value < minimum) { return false; }
        }
        if (Objects.nonNull(maximum) && Objects.nonNull(value)) {
            if (value > maximum) { return false; }
        }
        return true;
    }

    public static boolean stringValue(Object object, boolean notNull, Integer minimum, Integer maximum) {
        String value = object instanceof String? (String) object: null;
        if (notNull && Objects.isNull(value)) {
            return false;
        } 
        if (Objects.nonNull(minimum) && Objects.nonNull(value)) {
            if (value.length() < minimum) { return false; }
        }
        if (Objects.nonNull(maximum) && Objects.nonNull(value)) {
            if (value.length() > maximum) { return false; }
        }
        return true;
    }

    public static boolean password(Object object, boolean notNull, Integer minimum, Integer maximum) {
        String value = object instanceof String? (String) object: null;
        if (notNull && Objects.isNull(value)) {
            return false;
        } 
        if (Objects.nonNull(minimum) && Objects.nonNull(value)) {
            if (value.length() < minimum) { return false; }
        }
        if (Objects.nonNull(maximum) && Objects.nonNull(value)) {
            if (value.length() > maximum) { return false; }
        }
        if (Objects.nonNull(value)) {
            final boolean HAS_DIGIT = value.matches(".*\\d.*");
            final boolean HAS_SPECIAL_CHARS = value.matches(".*[\\W.|_]*");
            final boolean HAS_UPPERCASE_CHARS = value.matches(".*[A-Z].*");
            final boolean HAS_LOWCASE_CHARS = value.matches(".*[a-z].*");
            return HAS_DIGIT && HAS_SPECIAL_CHARS && HAS_UPPERCASE_CHARS && HAS_LOWCASE_CHARS;
        }
        return true;
    }

    public static boolean email(Object object, boolean notNull, Integer minimum, Integer maximum) {
        if (!stringValue(object, notNull, minimum, maximum)) {
            return false;
        }
        return ((String) object).matches("^[a-z]+[a-z0-9\\._]*@[a-z][a-z0-9\\._]*$");
    }

    public static boolean cpf(Object object, boolean notNull) {
        if (!stringValue(object, notNull, 11, 11)) {
            return false;
        }
        final String value = (String) object;
        final int[] CPF = IntStream.range(0, 11).map(i -> Integer.parseInt(value.substring(i, i + 1))).toArray();
        final int D1 = 10 * CPF[0]+ 9 * CPF[1]+ 8 * CPF[2]+ 7 * CPF[3]+ 6 * CPF[4]+ 5 * CPF[5]+ 4 * CPF[6]+ 3 * CPF[7]+ 2 * CPF[8];
        if (11 - D1 % 11 != CPF[9]) { return false; }
        final int D2 = 11 * CPF[0]+ 10 * CPF[1]+ 9 * CPF[2]+ 8 * CPF[3]+ 7 * CPF[4]+ 6 * CPF[5]+ 5 * CPF[6]+ 4 * CPF[7]+ 3 * CPF[8]+ 2 * CPF[9];
        return 11 - D2 % 11 == CPF[10];
    }
}

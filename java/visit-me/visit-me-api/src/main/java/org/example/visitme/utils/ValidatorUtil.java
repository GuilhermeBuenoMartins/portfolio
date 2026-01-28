package org.example.visitme.utils;

import java.util.stream.IntStream;

import org.hibernate.query.sqm.sql.internal.InstantiationException;

public final class ValidatorUtil {

    private ValidatorUtil() {
        throw new InstantiationException("This class cannot be instantiated.");
    }

    public static boolean validateLength(String value, Integer minimum, Integer maximum) {
        if (value == null) { return false; }
        if (minimum != null) {
            if (value.length() < minimum) { return false; }
        }
        if (maximum != null) {
            if (value.length() > maximum) { return false; }
        }
        return true;
    }
    
    public static boolean validateEmail(String email) {
        if (email == null) { return false; }
        return email.matches("^[a-z]+[a-z0-9\\._]*@[a-z][a-z0-9\\._]*$");
    }

    public static boolean validateCPF(String cpf) {
        final int LENGTH = 11;
        if (cpf == null ) { return false; }
        if (cpf.length() != LENGTH) { return false; }
        if (!cpf.matches("\\d{11}")) { return false; }
        final int[] digit = IntStream.range(0, LENGTH).map(i -> Integer.parseInt(cpf.substring(i, i + 1))).toArray();
        int calc = 10 * IntStream.range(0, LENGTH - 2).reduce(0, (r, i) -> (10 - i) * digit[i] + r) % 11;
        int verication = calc == 10? 0: calc;
        if (digit[9] != verication) { return false; }
        calc = 10 * IntStream.range(0, LENGTH - 1).reduce(0, (r, i) -> (11 - i) * digit[i] + r) % 11;
        verication = calc == 10? 0: calc;
        return digit[10] == verication;
    }
}

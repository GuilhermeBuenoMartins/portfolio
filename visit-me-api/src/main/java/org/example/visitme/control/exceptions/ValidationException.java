package org.example.visitme.control.exceptions;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends RuntimeException {

    private Set<ErrorException> errors;

    public ValidationException(Set<ErrorException> errors) {
        super(errors.toString());
        this.errors = errors;
    }
}

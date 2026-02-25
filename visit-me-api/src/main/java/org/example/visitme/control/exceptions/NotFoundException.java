package org.example.visitme.control.exceptions;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

    private Set<ErrorException> errors = new HashSet<>();

    public NotFoundException(String cause, String message) {
        super(String.format("%s: %s", cause, message));
        this.errors.add(new ErrorException(cause, message));
    }

}

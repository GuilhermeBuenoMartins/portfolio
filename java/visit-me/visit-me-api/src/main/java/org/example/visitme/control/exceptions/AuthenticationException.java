package org.example.visitme.control.exceptions;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

    private ErrorException error;

    public AuthenticationException(ErrorException error) {
        super(error.getMessage());
        this.error = error;
    }
}

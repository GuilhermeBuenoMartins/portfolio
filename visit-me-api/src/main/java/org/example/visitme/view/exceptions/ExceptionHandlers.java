package org.example.visitme.view.exceptions;

import java.time.Instant;
import java.util.Set;

import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.NotFoundException;
import org.example.visitme.control.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlers {
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> validation(ValidationException e, HttpServletRequest request) {
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(Instant.now(), STATUS, request.getRequestURI(), e.getErrors());
        return new ResponseEntity<ExceptionResponse>(response, STATUS);
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> authentication(AuthenticationException e, HttpServletRequest request) {
        final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;
        ExceptionResponse response = new ExceptionResponse(Instant.now(), STATUS, request.getRequestURI(), Set.of(e.getError()));
        return new ResponseEntity<ExceptionResponse>(response, STATUS);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFound(NotFoundException e, HttpServletRequest request) {
        final HttpStatus STATUS = HttpStatus.NOT_FOUND;
        ExceptionResponse response = new ExceptionResponse(Instant.now(), STATUS, request.getRequestURI(), e.getErrors());
        return new ResponseEntity<ExceptionResponse>(response, STATUS);
    }
}

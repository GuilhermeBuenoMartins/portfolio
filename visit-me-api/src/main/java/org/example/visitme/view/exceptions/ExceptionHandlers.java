package org.example.visitme.view.exceptions;

import java.time.Instant;

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
}

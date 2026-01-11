package org.example.visitme.view.exceptions;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.exceptions.ErrorException;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExceptionResponse {
    
    private Instant timestamp;

    private HttpStatus status;

    private String path;

    private Set<ErrorException> messages = new HashSet<>();

}

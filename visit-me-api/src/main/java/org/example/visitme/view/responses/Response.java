package org.example.visitme.view.responses;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response<T> {

    private Instant timestamp;

    private HttpStatus status;

    private String message;

    private T data;
    
}

package org.example.visitme.view.exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.exceptions.ErrorException;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Standard Exception Response Model")
public class ExceptionResponse implements Serializable {

    @Schema(description = "Timestamp of when the exception occurred", example = "2024-01-01T12:00:00Z")
    private Instant timestamp;

    @Schema(description = "HTTP status code of the exception", example = "404 BAD_REQUEST")
    private HttpStatus status;

    @Schema(description = "Path of the request that caused the exception", example = "/api/v1/resource")
    private String path;

    @Schema(description = "Set of error messages associated with the exception")
    private Set<ErrorException> messages = new HashSet<>();

}

package org.example.visitme.control.exceptions;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;

import org.example.visitme.view.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import tools.jackson.databind.json.JsonMapper;

@Getter
public class AuthenticationException extends RuntimeException implements AuthenticationEntryPoint {

    private ErrorException error;

    public AuthenticationException(ErrorException error) {
        super(error.getMessage());
        this.error = error;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {
        final Instant now = Instant.now();
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        final String path = request.getRequestURI();
        final Set<ErrorException> errors = Set.of(error);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(now, status, path, errors);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new JsonMapper().writeValueAsString(exceptionResponse));
    }
}

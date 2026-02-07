package org.example.visitme.view.controllers;

import org.example.visitme.view.exceptions.ExceptionResponse;
import org.example.visitme.view.requests.SignInRequest;
import org.example.visitme.view.requests.SignUpRequest;
import org.example.visitme.view.responses.HealthResponse;
import org.example.visitme.view.responses.Response;
import org.example.visitme.view.responses.SignUpResponse;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Home Controller", description = "Controller responsible for home endpoints.")
public interface HomeController {

        @Operation(summary = "Health Check", description = "Endpoint to check the health status of the application.")
        public ResponseEntity<Response<HealthResponse>> getHealth();

        @Operation(summary = "Sign Up", description = "Endpoint to register a new user.", responses = {
                        @ApiResponse(responseCode = "201", description = "User signed up successfully.", content = @Content(schema = @Schema(implementation = SignUpResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input data."),
        })
        public ResponseEntity<Response<SignUpResponse>> signUp(
                        @RequestBody(description = "Sign Up Request Body", required = true, content = @Content(schema = @Schema(implementation = SignUpRequest.class))) SignUpRequest request);

        @Operation(summary = "Sign In", description = "Endpoint to authenticate a user and provide a token.", responses = {
                        @ApiResponse(responseCode = "200", description = "User signed in successfully."),
                        @ApiResponse(responseCode = "400", description = "Invalid input data.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized access.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
        public ResponseEntity<Response<String>> signIn(
                        @RequestBody(description = "Sign In Request Body", required = true, content = @Content(schema = @Schema(implementation = SignInRequest.class))) SignInRequest request);

}

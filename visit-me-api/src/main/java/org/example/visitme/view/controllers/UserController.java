package org.example.visitme.view.controllers;

import java.util.Map;

import org.example.visitme.view.exceptions.ExceptionResponse;
import org.example.visitme.view.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Controller", description = "Controller responsible for user endpoints.")
public interface UserController {

@Operation(summary = "Sign Out", description = "Endpoint to authorize a user and invalidate a token.", 
        parameters = {
            @Parameter(
                name = "Authorization", 
                description = "Bearer token used to authorize user's operations in the system.",
                required = true,
                schema = @Schema(type = "string"))
        },
        responses = {
                        @ApiResponse(responseCode = "200", description = "User signed out from the system successfully."),
                        @ApiResponse(responseCode = "401", description = "Unauthorized access.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    ResponseEntity<Response<Boolean>> signOut(String authorization);
}

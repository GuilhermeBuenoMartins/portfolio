package org.example.visitme.view.controllers;

import org.example.visitme.view.exceptions.ExceptionResponse;
import org.example.visitme.view.responses.Response;
import org.example.visitme.view.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

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

    @Operation(summary = "List Users", description = "Endpoint to list users in the system, with pagination and optional filter by fullname.", 
        parameters = {
            @Parameter(
                name = "fullname", 
                description = "Optional parameter to filter users by fullname. It performs a case-insensitive search for users whose full name contains the provided value.",
                required = false,
                schema = @Schema(type = "string")),
            @Parameter(
                name = "page", 
                description = "Page number for pagination. Default is 0.",
                required = false,
                schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(
                name = "size", 
                description = "Number of records per page for pagination. Default is 5.",
                required = false,
                schema = @Schema(type = "integer", defaultValue = "5"))
        },
        responses = {
                        @ApiResponse(responseCode = "200", description = "Users listed successfully."),
                        @ApiResponse(responseCode = "401", description = "Unauthorized access.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    ResponseEntity<Page<UserResponse>> listUser(String fullname, int page, int size);
}

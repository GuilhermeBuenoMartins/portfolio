package org.example.visitme.view.requests;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Sign Up Request Model")
public class SignUpRequest {

    @Schema(description = "The login details of the user.", requiredMode = Schema.RequiredMode.REQUIRED)
    private LoginRequest login;

    @Schema(description = "The full name of the user.", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fullName;

    @Schema(description = "The CPF number of the user.", example = "12345678900", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cpf;

    @Schema(description = "The email address of the user.", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "The phone numbers of the user.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<PhoneRequest> phones;

}
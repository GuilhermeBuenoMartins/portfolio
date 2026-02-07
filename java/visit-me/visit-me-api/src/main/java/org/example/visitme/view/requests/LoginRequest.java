package org.example.visitme.view.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Login Request Model")
public class LoginRequest {

    @Schema(description = "The username of the user.", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "The password of the user.", example = "P@ssw0rd!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "The recovery password question.", example = "What is your pet's name?", requiredMode = Schema.RequiredMode.REQUIRED)
    private String recoveryPasswordQuestion;

    @Schema(description = "The recovery password answer.", example = "Fluffy", requiredMode = Schema.RequiredMode.REQUIRED)
    private String recoveryPasswordAnswer;

}

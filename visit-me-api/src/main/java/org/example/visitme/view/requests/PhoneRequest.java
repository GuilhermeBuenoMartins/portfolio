package org.example.visitme.view.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Phone Request Model")
public class PhoneRequest {

    @Schema(description = "The phone number of the user.", example = "11949555014", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
}

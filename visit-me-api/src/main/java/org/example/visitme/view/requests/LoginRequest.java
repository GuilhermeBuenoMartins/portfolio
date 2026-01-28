package org.example.visitme.view.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginRequest {
    
    private String username;

    private String password;

    private String recoveryPasswordQuestion;

    private String recoveryPasswordAnswer;

}

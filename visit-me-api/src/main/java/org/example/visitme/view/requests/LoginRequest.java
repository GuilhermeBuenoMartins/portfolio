package org.example.visitme.view.requests;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequest {
    
    private Object username;

    private Object password;

    private Object recoveryPasswordQuestion;

    private Object recoveryPasswordAnswer;

}

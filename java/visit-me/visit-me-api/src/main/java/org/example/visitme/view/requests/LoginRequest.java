package org.example.visitme.view.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginRequest {
    
    private Object username;

    private Object password;

    private Object recoveryPasswordQuestion;

    private Object recoveryPasswordAnswer;

}

package org.example.visitme.view.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SignInRequest {

    public String username;

    public String password;
}

package org.example.visitme.view.requests;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SignUpRequest {

    private LoginRequest login;

    private String fullName;

    private String cpf;

    private String email;

    private Set<PhoneRequest> phones;


}
package org.example.visitme.view.requests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRequest {

    private LoginRequest login;

    private Object fullName;

    private Object cpf;

    private Object email;

    private List<PhoneRequest> phones;


}
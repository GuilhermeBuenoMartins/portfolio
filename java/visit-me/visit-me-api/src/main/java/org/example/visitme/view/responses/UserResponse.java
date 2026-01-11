package org.example.visitme.view.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserResponse implements Serializable {

    private Integer id;

    private LoginResponse login;

    private String fullName;

    private String cpf;

    private String email;

    private List<PhoneResponse> phones = new ArrayList<>();
    
}

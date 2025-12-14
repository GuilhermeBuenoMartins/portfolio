package org.example.visitme.control.dto;

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
public class UserDto implements Serializable {

    private Integer id;

    private LoginDto login;

    private String fullName;

    private String cpf;

    private String email;

    private List<PhoneDto> phones = new ArrayList<>();
    
}

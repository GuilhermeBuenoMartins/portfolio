package org.example.visitme.control.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class LoginDto {
    
    private Integer id;

    private String username;

    private String password;

    private String recoveryPasswordQuestion;

    private String recoveryPasswordAnswer;

    private Boolean actived;

}

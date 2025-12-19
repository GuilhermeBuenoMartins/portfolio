package org.example.visitme.view.responses;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class LoginResponse implements Serializable {
    
    private Integer id;

    private String username;

    private String password;

    private String recoveryPasswordQuestion;

    private String recoveryPasswordAnswer;

    private Boolean actived;

}

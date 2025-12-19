package org.example.visitme.view.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class PhoneResponse {

    private Integer id;

    private String phone;

    @JsonIgnore
    private UserResponse user;
    
}

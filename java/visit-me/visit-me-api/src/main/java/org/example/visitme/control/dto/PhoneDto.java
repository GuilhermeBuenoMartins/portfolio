package org.example.visitme.control.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "phone")
public class PhoneDto {

    private Integer id;

    private String phone;

    @JsonIgnore
    private UserDto user;

    public PhoneDto(String phone) {
        this.phone = phone;
    }
}

package org.example.visitme.control.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorException {
    
    private String cause;

    private String message;

}

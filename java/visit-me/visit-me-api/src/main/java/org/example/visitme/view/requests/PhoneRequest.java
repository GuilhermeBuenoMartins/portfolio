package org.example.visitme.view.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PhoneRequest {

    private Object id;

    private Object phone;
}

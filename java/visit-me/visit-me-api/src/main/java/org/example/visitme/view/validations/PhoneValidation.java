package org.example.visitme.view.validations;

import org.example.visitme.utils.ValidatorUtil;
import org.springframework.stereotype.Component;

@Component
public class PhoneValidation {
 
    public boolean validationPhone(Object value) {
        String phone = (String) value;
        return ValidatorUtil.validateLength(phone, 10, 11);
    }
}

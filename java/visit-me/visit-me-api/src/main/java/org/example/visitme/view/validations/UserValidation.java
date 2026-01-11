package org.example.visitme.view.validations;

import org.example.visitme.utils.ValidatorUtil;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {

    public boolean validateFullName(Object value) {
        final String recoveryFullName = (String) value;
        return ValidatorUtil.validateLength(recoveryFullName, 8, 256);
    }

    public boolean validateCpf(Object value) {
        final String cpf = (String) value;
        return ValidatorUtil.validateCPF(cpf);
    }

    public boolean validateEmail(Object value) {
        final String email = (String) value;
        return ValidatorUtil.validateEmail(email);
    }
    
}

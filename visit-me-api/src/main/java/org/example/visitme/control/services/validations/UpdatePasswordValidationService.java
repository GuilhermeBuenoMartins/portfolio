package org.example.visitme.control.services.validations;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.utils.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordValidationService {
 
    public Set<ErrorException> validate(UserDto dto) {
        Set<ErrorException> errors = new HashSet<>();
        final String password_cause = "Field \"password\".";
        final String password_message = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        final String newPassword = dto.getLogin().getPassword();
        if (!ValidatorUtil.validateLength(newPassword, 8, null)) {
            errors.add(new ErrorException(password_cause, password_message));
        } else if (!newPassword.matches(".*\\d+.*") || !newPassword.matches(".*[\\W+|_+].*") || !newPassword.matches(".*[A-Z].*") || !newPassword.matches(".*[a-z].*")) {
            errors.add(new ErrorException(password_cause, password_message));
        }
        if (!ValidatorUtil.validateCPF(dto.getCpf())) {
            final String cause = "Path \"CPF\".";
            final String message = "The field must have: exactly 11 digits; no special characters.";
            errors.add(new ErrorException(cause, message));
        }
        return errors;
    }
}

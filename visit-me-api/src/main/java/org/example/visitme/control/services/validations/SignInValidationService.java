package org.example.visitme.control.services.validations;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.utils.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class SignInValidationService {

    public Set<ErrorException> validate(LoginDto dto) {
        Set<ErrorException> errors = new HashSet<>();
        if (!ValidatorUtil.validateLength(dto.getUsername(), 1, null)) {
            errors.add(new ErrorException("Field \"username\"", "Username cannot be null or empty."));
        }
        if (!ValidatorUtil.validateLength(dto.getPassword(), 1, null)) {
            errors.add(new ErrorException("Field \"password\"", "Password cannot be null or empty."));
        }
        return errors;
    }
}

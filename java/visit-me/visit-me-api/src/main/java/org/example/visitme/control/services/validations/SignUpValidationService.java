package org.example.visitme.control.services.validations;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpValidationService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;
    
    public Set<ErrorException> validate(UserDto userDto) {
        Set<ErrorException> errors = new HashSet<>();
        validateUsername(userDto.getLogin().getUsername(), errors);
        validatePassword(userDto.getLogin().getPassword(), errors);
        validateRecoveryPasswordQuestion(userDto.getLogin().getRecoveryPasswordQuestion(), errors);
        validateRecoveryPasswordAnswer(userDto.getLogin().getRecoveryPasswordAnswer(), errors);
        validateFullName(userDto.getFullName(), errors);
        validateCpf(userDto.getCpf(), errors);
        validateEmail(userDto.getEmail(), errors);
        validatePhones(userDto.getPhones(), errors);
        if (errors.size() == 0) {
            validateUniqueCpf(userDto.getCpf(), errors);
            validateUniqueUsername(userDto.getLogin().getUsername(), errors);
        }
        return errors;
    }

    private void validateUsername(String username, Set<ErrorException> errors) {
        final String CAUSE = "Field \"username\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces.";
        if (!ValidatorUtil.validateLength(username, 8, null)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        } else if (username.contains(" ")) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }

    private void validatePassword(String password, Set<ErrorException> errors) {
        final String CAUSE = "Field \"password\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        if (password == null) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        } else if (!password.matches(".*\\d+.*") || !password.matches(".*[\\W+|_+].*") || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*")) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }
    
    private void validateRecoveryPasswordQuestion(String recoveryPasswordQuestion, Set<ErrorException> errors) {
        final String CAUSE = "Field \"recoveryPasswordQuestion\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 256 characters.";
        if (!ValidatorUtil.validateLength(recoveryPasswordQuestion, 8, 256)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }

    private void validateRecoveryPasswordAnswer(String recoveryPasswordAnswer, Set<ErrorException> errors) {
        final String CAUSE = "Field \"recoveryPasswordAnswer\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 32 characters.";
        if (!ValidatorUtil.validateLength(recoveryPasswordAnswer, 8, 32)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }

    private void validateFullName(String fullname, Set<ErrorException> errors) {
        final String CAUSE = "Field \"fullname\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 96 characters.";
        if (!ValidatorUtil.validateLength(fullname, 8, 96)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }

    private void validateCpf(String cpf, Set<ErrorException> errors) {
        final String CAUSE = "Field \"cpf\".";
        final String MESSAGE = "The field must have: exactly 11 digits; no special characters.";
        if (!ValidatorUtil.validateCPF(cpf)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }

    private void validateEmail(String email, Set<ErrorException> errors) {
        final String CAUSE = "Field \"email\".";
        final String MESSAGE = "The field must have a maximum of 64 characters.";
        if (!ValidatorUtil.validateEmail(email)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }

    private void validatePhones(Set<PhoneDto> phoneDtos, Set<ErrorException> errors) {
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        if (phoneDtos == null) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
        else if (phoneDtos.size() == 0 || phoneDtos.size() > 2) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        } else {
            for (PhoneDto phoneDto : phoneDtos) {
                if (phoneDto == null) {
                    errors.add(new ErrorException(CAUSE, MESSAGE));
                    break;
                } else if (!ValidatorUtil.validateLength(phoneDto.getPhone(), 10, 11)) {
                    errors.add(new ErrorException(CAUSE, MESSAGE));
                    break;
                }
            }
        }
    }
    
    private void validateUniqueUsername(String username, Set<ErrorException> errors) {
        final String CAUSE = "Field \"username\".";
        final String MESSAGE = "Username was already registered.";
        if (loginRepository.existsByUsername(username)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }

    private void validateUniqueCpf(String cpf, Set<ErrorException> errors) {
        final String CAUSE = "Field\"cpf\".";
        final String MESSAGE = "CPF was already registered.";
        if (userRepository.existsByCpf(cpf)) {
            errors.add(new ErrorException(CAUSE, MESSAGE));
        }
    }
}

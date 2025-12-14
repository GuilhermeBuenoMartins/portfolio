package org.example.visitme.control.services;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.control.exceptions.ValidationException;
import org.example.visitme.utils.ValidatorUtil;
import org.example.visitme.view.requests.PhoneRequest;
import org.example.visitme.view.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    public void signUp(UserRequest request) {
        Set<ErrorException> errors = new HashSet<>();
        final String USERNAME_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces.";
        final String PASSWORD_ERROR_MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        final String RECOVERY_PASSWORD_QUESTION_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 256 characters.";
        final String RECOVERY_PASSWORD_ANSWER_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 32 characters.";
        final String FULL_NAME_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 96 characters.";
        final String CPF_ERROR_MESSAGE = "The field must have: exactly 11 digits; no special characters.";
        final String EMAIL_ERROR_MESSAGE = "The field must have a maximum of 64 characters.";
        final String PHONE_ERROR_MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        final String EXISTING_USERNAME_MESSAGE_ERROR = "Username was already registered.";
        final String EXISTING_CPF_MESSAGE_ERROR = "CPF was already registered.";
        if (!ValidatorUtil.stringValue(request.getLogin().getUsername(), true, null, 64)) {
            errors.add(new ErrorException("username", USERNAME_ERROR_MESSAGE));
        } else if (((String) request.getLogin().getUsername()).contains(" ")) {
            errors.add(new ErrorException("username", USERNAME_ERROR_MESSAGE));
        }
        if (!ValidatorUtil.password(request.getLogin().getPassword(), true, 8, null)) {
            errors.add(new ErrorException("password", PASSWORD_ERROR_MESSAGE));
        }
        if (!ValidatorUtil.stringValue(request.getLogin().getRecoveryPasswordQuestion(), true, 8, 256)) {
            errors.add(new ErrorException("recoveryPasswordQuestion", RECOVERY_PASSWORD_QUESTION_ERROR_MESSAGE));
        }
        if (!ValidatorUtil.stringValue(request.getLogin().getRecoveryPasswordAnswer(), true, 8, 32)) {
            errors.add(new ErrorException("recoveryPasswordAnswer", RECOVERY_PASSWORD_ANSWER_ERROR_MESSAGE));
        }
        if (!ValidatorUtil.stringValue(request.getFullName(), true, 8, 96)) {
            errors.add(new ErrorException("fullName", FULL_NAME_ERROR_MESSAGE));
        }
        if (!ValidatorUtil.cpf(request.getCpf(), true)) {
            errors.add(new ErrorException("cpf", CPF_ERROR_MESSAGE));
        }
        if (!ValidatorUtil.email(request.getEmail(), false, null, 64)) {
            errors.add(new ErrorException("email", EMAIL_ERROR_MESSAGE));
        }
        if (request.getPhones().size() < 1 || request.getPhones().size() > 2) {
            errors.add(new ErrorException("phones", PHONE_ERROR_MESSAGE));
        } else {
            for (PhoneRequest phone: request.getPhones()) {
                if (!ValidatorUtil.stringValue(phone.getPhone(), true, 10, 11)) {
                    errors.add(new ErrorException("phones", PHONE_ERROR_MESSAGE));
                } else if (((String) phone.getPhone()).matches("\\D")) {
                    errors.add(new ErrorException("phones", PHONE_ERROR_MESSAGE));
                }

            }
        }
        if (errors.size() == 0) {
            if (loginService.hasUsername((String) request.getLogin().getUsername())) {
                errors.add(new ErrorException("username", EXISTING_USERNAME_MESSAGE_ERROR));
            }
            if (userService.hasCpf((String) request.getCpf())) {
                errors.add(new ErrorException("cpf", EXISTING_CPF_MESSAGE_ERROR));
            }
        }
        if (errors.size() != 0) {
            throw new ValidationException(errors);
        }
    }
}

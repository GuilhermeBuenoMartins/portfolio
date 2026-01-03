package org.example.visitme.view.validations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.control.exceptions.ValidationException;
import org.example.visitme.control.services.LoginService;
import org.example.visitme.control.services.UserService;
import org.example.visitme.view.requests.LoginRequest;
import org.example.visitme.view.requests.PhoneRequest;
import org.example.visitme.view.requests.SignInRequest;
import org.example.visitme.view.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class RequestValidation {

    private final String USERNAME_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces.";

    private final String PASSWORD_ERROR_MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";

    private final String RECOVERY_PASSWORD_QUESTION_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 256 characters.";

    private final String RECOVERY_PASSWORD_ANSWER_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 32 characters.";
    
    private final String FULL_NAME_ERROR_MESSAGE = "The field must have: minimum of 8 characters; maximum of 96 characters.";

    private final String CPF_ERROR_MESSAGE = "The field must have: exactly 11 digits; no special characters.";

    private final String EMAIL_ERROR_MESSAGE = "The field must have a maximum of 64 characters.";

    private final String PHONE_ERROR_MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";

    private final String EXISTING_USERNAME_MESSAGE_ERROR = "Username was already registered.";

    private final String EXISTING_CPF_MESSAGE_ERROR = "CPF was already registered.";

    private final String EMPTY_USERNAME = "Username cannot be null or empty.";

    private final String EMPTY_PASSWORD = "Password cannot be null or empty.";

    private Set<ErrorException> errors = new HashSet<>();

    @Autowired
    private LoginValidation loginValidation;

    @Autowired
    private PhoneValidation phoneValidation;

    @Autowired
    private UserValidation userValidation;
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    public void validateSignUp(UserRequest userRequest) {
        final LoginRequest loginRequest = userRequest.getLogin() == null? new LoginRequest(): userRequest.getLogin();
        final List<PhoneRequest> phoneRequests = userRequest.getPhones() == null? new ArrayList<>(): userRequest.getPhones();
        errors.clear();
        // Login Validations
        if (!loginValidation.validateUsername(loginRequest.getUsername())) { errors.add(new ErrorException("username", USERNAME_ERROR_MESSAGE)); }
        if (!loginValidation.validatePassword(loginRequest.getPassword())) { errors.add(new ErrorException("password", PASSWORD_ERROR_MESSAGE)); }
        if (!loginValidation.validateRecoveryPasswordQuestion(loginRequest.getRecoveryPasswordQuestion())) { errors.add(new ErrorException("recoveryPasswordQuestion", RECOVERY_PASSWORD_QUESTION_ERROR_MESSAGE)); }
        if (!loginValidation.validateRecoveryPasswordAnswer(loginRequest.getRecoveryPasswordAnswer())) { errors.add(new ErrorException("recoveryPasswordAnswer", RECOVERY_PASSWORD_ANSWER_ERROR_MESSAGE)); }
        // User Validations
        if (!userValidation.validateFullName(userRequest.getFullName())) { errors.add(new ErrorException("fullName", FULL_NAME_ERROR_MESSAGE)); }
        if (!userValidation.validateCpf(userRequest.getCpf())) { errors.add(new ErrorException("cpf", CPF_ERROR_MESSAGE)); }
        if (!userValidation.validateEmail(userRequest.getEmail())) { errors.add(new ErrorException("email", EMAIL_ERROR_MESSAGE)); }
        // Phone Validations
        if (phoneRequests.size() < 1 || phoneRequests.size() > 2) {
            errors.add(new ErrorException("phones", PHONE_ERROR_MESSAGE));
        } else if (phoneRequests.stream().anyMatch(phone -> !phoneValidation.validationPhone(phone.getPhone()))) {
            errors.add(new ErrorException("phones", PHONE_ERROR_MESSAGE));
        }
        // Databases validations
        if (errors.size() == 0) {
            if (loginService.hasUsername((String) loginRequest.getUsername())) { errors.add(new ErrorException("username", EXISTING_USERNAME_MESSAGE_ERROR)); }
            if (userService.hasCpf((String) userRequest.getCpf())) { errors.add(new ErrorException("cpf", EXISTING_CPF_MESSAGE_ERROR)); }
        }
        if (errors.size() != 0 ) { throw new ValidationException(errors); }
    }

    public void validateSignIn(SignInRequest request) {
        errors.clear();
        if (request.getUsername() == null) {
            errors.add(new ErrorException("username", EMPTY_USERNAME));
        } else if (request.getUsername().isBlank()) {
            errors.add(new ErrorException("username", EMPTY_USERNAME));
        }
        if (request.getPassword() == null) {
            errors.add(new ErrorException("password", EMPTY_PASSWORD));
        } else if (request.getPassword().isBlank()) {
            errors.add(new ErrorException("password", EMPTY_PASSWORD));
        }
        if (errors.size() > 0) { throw new ValidationException(errors); }
    }

}

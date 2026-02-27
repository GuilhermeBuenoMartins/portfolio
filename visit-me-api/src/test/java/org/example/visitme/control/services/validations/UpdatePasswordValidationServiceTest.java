package org.example.visitme.control.services.validations;

import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.ErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Test of UpdatePasswordValidationService")
public class UpdatePasswordValidationServiceTest {

    @Autowired
    private UpdatePasswordValidationService validationService;
    
    @Test
    @DisplayName("Validate a valid CPF and password")
    void testValidateAValidCpfAndPassword() {
        UserDto dto = new UserDto();
        dto.setCpf("43746627079");
        LoginDto login = new LoginDto();
        login.setPassword("Abcdef1!");
        dto.setLogin(login);
        Set<ErrorException> errors = validationService.validate(dto);
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Validate an invalid CPF and password")
    void testValidateInvalidCpfAndPassword() {
        final String password_cause = "Field \"password\".";
        final String password_message = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";  
        final String cause = "Path \"CPF\".";
        final String message = "The field must have: exactly 11 digits; no special characters.";
        final Set<ErrorException> expected = Set.of(
            new ErrorException(password_cause, password_message), new ErrorException(cause, message));
        UserDto dto = new UserDto();
        dto.setCpf("1234567891");
        LoginDto login = new LoginDto();
        login.setPassword("abcdefg");
        dto.setLogin(login);
        Set<ErrorException> errors = validationService.validate(dto);
        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertEquals(expected, errors);
    }

    @Test
    @DisplayName("Validate a null password")
    void testValidateNullPassword() {
        final String cause = "Field \"password\".";
        final String message = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        final Set<ErrorException> expected = Set.of(new ErrorException(cause, message));
        UserDto dto = new UserDto();
        dto.setCpf("43746627079");
        LoginDto login = new LoginDto();
        dto.setLogin(login);
        Set<ErrorException> errors = validationService.validate(dto);
        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertEquals(expected, errors);
    }
}

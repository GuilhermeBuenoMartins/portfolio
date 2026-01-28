package org.example.visitme.control.services.validations;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.control.services.HomeService;
import org.example.visitme.model.repositories.LoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Test of SignInValidationService")
public class SignInValidationServiceTest {
    
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private HomeService homeService;
    
    @Autowired
    private SignInValidationService validationService;
    
    private LoginDto defaultLoginDto() {
        return new LoginDto("OtherUsername", "Pa$$w0rd", "What is a question?", "This is answer.");
    }

    private Set<PhoneDto> defaultPhoneDtos() {
        Set<PhoneDto> phoneDtos = new HashSet<>();
        phoneDtos.add(new PhoneDto("0143218765"));
        phoneDtos.add(new PhoneDto("01912348765"));
        return phoneDtos;
    }

    private UserDto defaultUserDto() {
        final String FULLNAME = "fullname";
        final String CPF = "04629933883";
        final String EMAIL = "a_user.name@domain.com";
        return new UserDto(defaultLoginDto(),FULLNAME, CPF, EMAIL, defaultPhoneDtos());
    }

    @Test
    @DisplayName("There should not be any error.")
    public void testNoErrors() {
        final int EXPECTED_SIZE = 0;
        final UserDto USER_DTO = defaultUserDto();
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(USER_DTO.getLogin());
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
    }

    @Test
    @DisplayName("Username should not be blank")
    public void testUsernameIsBlank() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"username\"";
        final String MESSAGE = "Username cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setUsername("");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto.getLogin());
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }



    @Test
    @DisplayName("Username should not be null")
    public void testUsernameIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"username\"";
        final String MESSAGE = "Username cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setUsername(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto.getLogin());
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Password should not be blank")
    public void testPasswordIsBlank() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"password\"";
        final String MESSAGE = "Password cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setPassword("");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto.getLogin());
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Password should not be null")
    public void testPasswordIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"password\"";
        final String MESSAGE = "Password cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setPassword(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto.getLogin());
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }
}

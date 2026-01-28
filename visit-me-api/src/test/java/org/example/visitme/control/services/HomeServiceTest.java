package org.example.visitme.control.services;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.control.exceptions.ValidationException;
import org.example.visitme.model.entities.LoginEntity;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Test of Home Service")
public class HomeServiceTest {

    @Autowired
    private HomeService homeService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    private LoginDto defaultLoginDto() {
        return new LoginDto("username", "Pa$$w0rd", "What is a question?", "This is answer.");
    }

    private Set<PhoneDto> defaultPhoneDtos() {
        Set<PhoneDto> phoneDtos = new HashSet<>();
        phoneDtos.add(new PhoneDto("0143218765"));
        phoneDtos.add(new PhoneDto("01912348765"));
        return phoneDtos;
    }

    private UserDto defaultUserDto() {
        final String FULLNAME = "fullname";
        final String CPF = "04629932801";
        final String EMAIL = "a_user.name@domain.com";
        return new UserDto(defaultLoginDto(),FULLNAME, CPF, EMAIL, defaultPhoneDtos());
    }

    @Test
    @DisplayName("Valid user data should be stored")
    public void testSignUpWithValidUserData() {
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setUsername("AnotherUsername");
        userDto.setCpf("04629934855");
        final UserDto USER_DTO = homeService.signUp(userDto);
        Assertions.assertNotNull(USER_DTO.getId());
        Assertions.assertNotNull(USER_DTO.getLogin().getId());
        Assertions.assertTrue(USER_DTO.getLogin().getActived());
        USER_DTO.getPhones().forEach(phoneDto -> Assertions.assertNotNull(phoneDto.getId()));
        Assertions.assertTrue(userRepository.findById(USER_DTO.getId()).isPresent());
    }

    @Test
    @DisplayName("Invalid user data should not be stored")
    public void testSignUpWithInvalidUserData() {
        final UserDto USER_DTO = new UserDto(new LoginDto(), null, null, null, new HashSet<>());
        final ErrorException[] ERROR_EXCEPTIONS = {
            new ErrorException("Field \"fullname\".", "The field must have: minimum of 8 characters; maximum of 96 characters."),
            new ErrorException("Field \"cpf\".", "The field must have: exactly 11 digits; no special characters."),
            new ErrorException("Field \"password\".", "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit."),
            new ErrorException("Field \"phone\".", "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits."),
            new ErrorException("Field \"username\".", "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces."),
            new ErrorException("Field \"recoveryPasswordQuestion\".", "The field must have: minimum of 8 characters; maximum of 256 characters."),
            new ErrorException("Field \"recoveryPasswordAnswer\".", "The field must have: minimum of 8 characters; maximum of 32 characters."),
            new ErrorException("Field \"email\".", "The field must have a maximum of 64 characters."),
        };
        try {
            homeService.signUp(USER_DTO);
        } catch (ValidationException exception) {
            Assertions.assertArrayEquals(ERROR_EXCEPTIONS, exception.getErrors().toArray());
        }
    }

    @Test
    @DisplayName("Valid login data should be authenticated")
    public void testSignInWithValidUserData() {
        final int TOKEN_LENGTH = 186;
        final UserDto USER_DTO = defaultUserDto();
        if (!loginRepository.existsByUsername(USER_DTO.getLogin().getUsername())) {
            homeService.signUp(USER_DTO);
        }
        final String TOKEN = homeService.signIn(defaultLoginDto());
        Assertions.assertNotNull(TOKEN);
        Assertions.assertEquals(TOKEN_LENGTH, TOKEN.length());
    }

    @Test
    @DisplayName("Login with blank username should be invalid")
    public void testLoginWithBlankUsername() {
        final int ERRORS_SIZE = 1;
        final String CAUSE = "Field \"username\"";
        final String MESSAGE = "Username cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setUsername("");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (ValidationException exception) {
            Assertions.assertEquals(ERRORS_SIZE, exception.getErrors().size());
            Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), exception.getErrors().toArray()[0]);
        }
    }

    @Test
    @DisplayName("Login with blank password should be invalid")
    public void testLoginWithBlankPassword() {
        final int ERRORS_SIZE = 1;
        final String CAUSE = "Field \"password\"";
        final String MESSAGE = "Password cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setPassword("");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (ValidationException exception) {
            Assertions.assertEquals(ERRORS_SIZE, exception.getErrors().size());
            Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), exception.getErrors().toArray()[0]);
        }
    }

    @Test
    @DisplayName("Invalid login with incorrect username should not authenticated")
    public void testInvalidLoginWithIncorrectUsername() {
        final String CAUSE = "Fields \"username\" or \"password\".";
        final String MESSAGE = "Usersanme or password are incorrect.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setUsername("incorrectUsername");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), exception.getError());
        }
    }

    @Test
    @DisplayName("Invalid login with incorrect password should not authenticated")
    public void testInvalidLoginWithIncorrectPassword() {
        final String CAUSE = "Fields \"username\" or \"password\".";
        final String MESSAGE = "Usersanme or password are incorrect.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setPassword("inco#ectPa$w0rd");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), exception.getError());
        }
    }

    @Test
    @DisplayName("Deactivated login should be not authenticated")
    public void testDeactivatedLogin() {
        final String CAUSE = "Fields \"username\" or \"password\".";
        final String MESSAGE = "Usersanme or password are incorrect.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            userDto = homeService.signUp(userDto);
            userDto.getLogin().setActived(false);
            loginRepository.save(ConverterUtil.from(userDto.getLogin(), LoginEntity.class));
        }
        try {
            homeService.signIn(defaultLoginDto());
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), exception.getError());
        }
    }
}

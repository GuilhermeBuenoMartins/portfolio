package org.example.visitme.control.services;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.control.exceptions.NotFoundException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        final String fullName = "fullname";
        final String cpf = "04629932801";
        final String email = "a_user.name@domain.com";
        return new UserDto(defaultLoginDto(), fullName, cpf, email, defaultPhoneDtos());
    }

    @Test
    @DisplayName("Valid user data should be stored")
    public void testSignUpWithValidUserData() {
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setUsername("AnotherUsername");
        userDto.setCpf("04629934855");
        userDto = homeService.signUp(userDto);
        Assertions.assertNotNull(userDto.getId());
        Assertions.assertNotNull(userDto.getLogin().getId());
        Assertions.assertTrue(userDto.getLogin().getActived());
        userDto.getPhones().forEach(phoneDto -> Assertions.assertNotNull(phoneDto.getId()));
        Assertions.assertTrue(userRepository.findById(userDto.getId()).isPresent());
    }

    @Test
    @DisplayName("Invalid user data should not be stored")
    public void testSignUpWithInvalidUserData() {
        final UserDto userDto = new UserDto(new LoginDto(), null, null, null, new HashSet<>());
        final ErrorException[] errorExceptions = {
                new ErrorException("Field \"fullname\".",
                        "The field must have: minimum of 8 characters; maximum of 96 characters."),
                new ErrorException("Field \"cpf\".", "The field must have: exactly 11 digits; no special characters."),
                new ErrorException("Field \"password\".",
                        "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit."),
                new ErrorException("Field \"phone\".",
                        "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits."),
                new ErrorException("Field \"username\".",
                        "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces."),
                new ErrorException("Field \"recoveryPasswordQuestion\".",
                        "The field must have: minimum of 8 characters; maximum of 256 characters."),
                new ErrorException("Field \"recoveryPasswordAnswer\".",
                        "The field must have: minimum of 8 characters; maximum of 32 characters."),
                new ErrorException("Field \"email\".", "The field must have a maximum of 64 characters."),
        };
        try {
            homeService.signUp(userDto);
        } catch (ValidationException exception) {
            Assertions.assertArrayEquals(errorExceptions, exception.getErrors().toArray());
        }
    }

    @Test
    @DisplayName("Valid login data should be authenticated")
    public void testSignInWithValidUserData() {
        final int tokenLength = 178;
        final UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        final String token = homeService.signIn(defaultLoginDto());
        Assertions.assertNotNull(token);
        Assertions.assertEquals(tokenLength, token.length());
    }

    @Test
    @DisplayName("Login with blank username should be invalid")
    public void testLoginWithBlankUsername() {
        final int errorsSize = 1;
        final String cause = "Field \"username\"";
        final String message = "Username cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setUsername("");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (ValidationException exception) {
            Assertions.assertEquals(errorsSize, exception.getErrors().size());
            Assertions.assertEquals(new ErrorException(cause, message), exception.getErrors().toArray()[0]);
        }
    }

    @Test
    @DisplayName("Login with blank password should be invalid")
    public void testLoginWithBlankPassword() {
        final int errorsSize = 1;
        final String cause = "Field \"password\"";
        final String message = "Password cannot be null or empty.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setPassword("");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (ValidationException exception) {
            Assertions.assertEquals(errorsSize, exception.getErrors().size());
            Assertions.assertEquals(new ErrorException(cause, message), exception.getErrors().toArray()[0]);
        }
    }

    @Test
    @DisplayName("Invalid login with incorrect username should not authenticated")
    public void testInvalidLoginWithIncorrectUsername() {
        final String cause = "Fields \"username\" or \"password\".";
        final String message = "Username or password are incorrect.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setUsername("incorrectUsername");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getError());
        }
    }

    @Test
    @DisplayName("Invalid login with incorrect password should not authenticated")
    public void testInvalidLoginWithIncorrectPassword() {
        final String cause = "Fields \"username\" or \"password\".";
        final String message = "Username or password are incorrect.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setPassword("inco#ectPa$w0rd");
        try {
            homeService.signIn(userDto.getLogin());
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getError());
        }
    }

    @Test
    @DisplayName("Deactivated login should be not authenticated")
    public void testDeactivatedLogin() {
        final String cause = "Fields \"username\" or \"password\".";
        final String message = "Username or password are incorrect.";
        UserDto userDto = defaultUserDto();
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            userDto = homeService.signUp(userDto);
            userDto.getLogin().setActived(false);
            loginRepository.save(ConverterUtil.from(userDto.getLogin(), LoginEntity.class));
        }
        try {
            homeService.signIn(defaultLoginDto());
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getError());
        }
    }

    @Test
    @DisplayName("Existent CPF should return a recovery password question")
    public void testGetRecoveryPasswordQuestionWithExistentCpf() {
        final UserDto userDto = defaultUserDto();
        final String recoveryPasswordQuestion = userDto.getLogin().getRecoveryPasswordQuestion();
        final String cpf = userDto.getCpf();
        if (!userRepository.existsByCpf(cpf)) {
            homeService.signUp(userDto);
        }
        Assertions.assertEquals(recoveryPasswordQuestion, homeService.getRecoveryPasswordQuestion(cpf));
    }

    @Test
    @DisplayName("Nonexistent CPF should not return a recovery password question")
    public void testGetRecoveryPasswordQuestionWithNonexistentCpf() {
        final String cause = "Path \"CPF\".";
        final String message = "This CPF does not exist in the system. Please, sign up.";
        final String cpf = "68874221070";
        Assertions.assertFalse(userRepository.existsByCpf(cpf));
        try {
            homeService.getRecoveryPasswordQuestion(cpf);
        } catch (NotFoundException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getErrors().toArray()[0]);
        }
    }

    @Test
    @DisplayName("Invalid CPF should not return a recovery password question")
    public void testGetRecoveryPasswordQuestionWithInvalidCpf() {
        final String cause = "Path \"CPF\".";
        final String message = "The field must have: exactly 11 digits; no special characters.";
        final String cpf = "04629932802";
        Assertions.assertFalse(userRepository.existsByCpf(cpf));
        try {
            homeService.getRecoveryPasswordQuestion(cpf);
        } catch (ValidationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getErrors().toArray()[0]);
        }
    }

    @Test
    @DisplayName("Update password with valid answer and CPF should be successful")
    public void testUpdatePasswordWithValidAnswerAndCpf() {
        final String newPassword = "NewP@ssw0rd";
        final String cpf = "69086398006";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDto userDto = defaultUserDto();
        userDto.setCpf(cpf);
        userDto.getLogin().setUsername("validAnswer&CPF");
        if (!userRepository.existsByCpf(cpf)) {
            homeService.signUp(userDto);
        }
        userDto = defaultUserDto();
        userDto.setCpf(cpf);
        userDto.getLogin().setUsername("validAnswer&CPF");
        userDto.getLogin().setPassword(newPassword);
        LoginDto loginDto = homeService.updatePassword(userDto);
        Assertions.assertNotNull(loginDto);
        Assertions.assertTrue(encoder.matches(newPassword, loginDto.getPassword()));
    }

    @Test
    @DisplayName("Update password with valid answer and invalid CPF should not be successful")
    public void testUpdatePasswordWithValidAnswerAndInvalidCpf() {
        final String cause = "Path \"CPF\".";
        final String message = "The field must have: exactly 11 digits; no special characters.";
        final String newPassword = "NewP@ssw0rd";
        final String cpf = "69086398005";
        UserDto userDto = defaultUserDto();
        if (!userRepository.existsByCpf(userDto.getCpf())) {
            homeService.signUp(userDto);
        }
        userDto = defaultUserDto();
        userDto.setCpf(cpf);
        userDto.getLogin().setPassword(newPassword);
        try {
            homeService.updatePassword(userDto);
        } catch (ValidationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getErrors().toArray()[0]);
        }
    }
    
    @Test
    @DisplayName("Update password with valid answer and nonexistent CPF should not be successful")
    public void testUpdatePasswordWithValidAnswerAndNonexistentCpf() {
        final String cause = "Path \"CPF\".";
        final String message = "This CPF does not exist in the system. Please, sign up.";
        final String newPassword = "NewP@ssw0rd";
        final String cpf = "68495419009";
        UserDto userDto = defaultUserDto();
        if (!userRepository.existsByCpf(userDto.getCpf())) {
            homeService.signUp(userDto);
        }
        userDto.setCpf(cpf);
        userDto.getLogin().setPassword(newPassword);
        try {
            homeService.updatePassword(userDto);
        } catch (NotFoundException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getErrors().toArray()[0]);
        }
    }

    @Test
    @DisplayName("Update password with invalid answer and valid CPF should not be successful")
    public void testUpdatePasswordWithInvalidAnswerAndValidCpf() {
        final String cause = "Field \"recoveryPasswordAnswer\".";
        final String message = "Incorrect answer! You are unauthorized to change password.";
        final String recoveryPasswordAnswer = "invalid answer";
        final String newPassword = "NewP@ssw0rd";
        UserDto userDto = defaultUserDto();
        if (!userRepository.existsByCpf(userDto.getCpf())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setRecoveryPasswordAnswer(recoveryPasswordAnswer);
        userDto.getLogin().setPassword(newPassword);
        try {
            homeService.updatePassword(userDto);
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getError());
        }
    }
    
    @Test
    @DisplayName("Update password with invalid password should not be successful")
    public void testUpdatePasswordWithInvalidPassword() {
        final String cause = "Field \"password\".";
        final String message = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        final String newPassword = "NewPassw0rd";
        UserDto userDto = defaultUserDto();
        if (!userRepository.existsByCpf(userDto.getCpf())) {
            homeService.signUp(userDto);
        }
        userDto.getLogin().setPassword(newPassword);
        try {
            homeService.updatePassword(userDto);
        } catch (ValidationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getErrors().toArray()[0]);
        }
    }
}

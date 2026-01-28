package org.example.visitme.control.services.validations;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.model.entities.UserEntity;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Test of SignUpValidationService")
public class SignUpValidationServiceTest {

    @Autowired
    public LoginRepository loginRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public SignUpValidationService validationService;
    
    private LoginDto defaultLoginDto() {
        return new LoginDto("AUsername", "Pa$$w0rd", "What is a question?", "This is answer.");
    }

    private Set<PhoneDto> defaultPhoneDtos() {
        Set<PhoneDto> phoneDtos = new HashSet<>();
        phoneDtos.add(new PhoneDto("0143218765"));
        phoneDtos.add(new PhoneDto("01912348765"));
        return phoneDtos;
    }

    private UserDto defaultUserDto() {
        final String FULLNAME = "fullname";
        final String CPF = "03452883868";
        final String EMAIL = "a_user.name@domain.com";
        return new UserDto(defaultLoginDto(),FULLNAME, CPF, EMAIL, defaultPhoneDtos());
    }

    @Test
    @DisplayName("There should not be any error.")
    public void testNoErrors() {
        final int EXPECTED_SIZE = 0;
        final UserDto USER_DTO = defaultUserDto();
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(USER_DTO);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
    }


    @Test
    @DisplayName("Username should not contain less than 8 characters")
    public void testUsernameWith7characters() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"username\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces.";
        final UserDto userDto = defaultUserDto();
        userDto.getLogin().setUsername("user123");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Username should not contain spaces")
    public void testUsernameWithSpace() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"username\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces.";
        final UserDto userDto = defaultUserDto();
        userDto.getLogin().setUsername("user name");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Username should not be null")
    public void testUsernameIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"username\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 64 characters; no spaces.";
        final UserDto userDto = defaultUserDto();
        userDto.getLogin().setUsername(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Password should contain at least one digit")
    public void testPasswordWithoutDigit() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"password\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setPassword("Pa$$word");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Password shoud contain at leat one special character")
    public void testPasswordWithoutSpecialCharacter() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"password\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setPassword("Passw0rd");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Password should contain at leat one uppercase character")
    public void testPasswordWithoutUppercase() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"password\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setPassword("pa$$w0rd");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Password should contain at least one lowercase character")
    public void testPasswordWithoutLowercase() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"password\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setPassword("PA$$W0RD");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Password should not be null")
    public void testPasswordIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"password\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setPassword(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Recovery password question should not contain less than 8 characters")
    public void testRecoverPasswordQuestionWith7characters() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"recoveryPasswordQuestion\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 256 characters.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setRecoveryPasswordQuestion("And so?");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);

    }

    @Test
    @DisplayName("Recover password question should not contain more than 256 characters")
    public void testRecoveryPasswordQuestionWith257characters() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"recoveryPasswordQuestion\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 256 characters.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setRecoveryPasswordQuestion(
            "Recovery password question is a method used to identity the authenticity of a user when is not possible through username and password. " + // 135
            "Thus, the system will use another information to question something that just user knows to answer correctly. What is your question?" // 132 = 267
        );
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Recover password answer should not contain less than 7 characters")
    public void testRecoveryPasswordAnswerWith7characters() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"recoveryPasswordAnswer\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 32 characters.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setRecoveryPasswordAnswer("Answer.");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Recover password answer should not contain more than 32 characters")
    public void testRecoveryPasswordAnswerWith33characters() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"recoveryPasswordAnswer\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 32 characters.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setRecoveryPasswordAnswer("Unfortunately, this is my answer.");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Recover password answer should not be null")
    public void testRecoveryPasswordAnswerIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"recoveryPasswordAnswer\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 32 characters.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setRecoveryPasswordAnswer(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Recover password question should not be null")
    public void testRecoveryPasswordQuestionIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"recoveryPasswordQuestion\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 256 characters.";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setRecoveryPasswordQuestion(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Fullname should not contain less than 8 characters")
    public void testFullnameWith8Characters() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"fullname\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 96 characters.";
        final UserDto userDto = defaultUserDto();
        userDto.setFullName("Minimum");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Fullname should not contain more than 96 characters")
    public void testFullnameWith96Characters() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"fullname\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 96 characters.";
        final UserDto userDto = defaultUserDto();
        userDto.setFullName("This is a fullname so much long that exceed the limit of 96 characters in the request. 0987654321");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Fullname should not be null")
    public void testFullnameIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"fullname\".";
        final String MESSAGE = "The field must have: minimum of 8 characters; maximum of 96 characters.";
        final UserDto userDto = defaultUserDto();
        userDto.setFullName(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("CPF should not be null")
    public void testCpfIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"cpf\".";
        final String MESSAGE = "The field must have: exactly 11 digits; no special characters.";
        UserDto userDto = defaultUserDto();
        userDto.setCpf(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);

    }

    @Test
    @DisplayName("Email should not be null")
    public void testEmailIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"email\".";
        final String MESSAGE = "The field must have a maximum of 64 characters.";
        UserDto userDto = defaultUserDto();
        userDto.setEmail(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Phones should contain less than 3 phone numbers")
    public void testPhonesWith3PhoneNumbers() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        UserDto userDto = defaultUserDto();
        userDto.getPhones().add(new PhoneDto("21956423187"));;
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Phones should contain at least one phone numbers")
    public void testPhonesWithoutPhoneNumbers() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        UserDto userDto = defaultUserDto();
        userDto.setPhones(new HashSet<>());
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Each phone number should contain more than 9 digits")
    public void testPhoneNumberWith9diigits() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        UserDto userDto = defaultUserDto();
        PhoneDto phoneDto = userDto.getPhones().toArray(new PhoneDto[userDto.getPhones().size()])[0];
        userDto.getPhones().remove(phoneDto);
        phoneDto.setPhone(phoneDto.getPhone().substring(0, 9));
        userDto.getPhones().add(phoneDto);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Each phone number should contain less than 12 digits")
    public void testPhoneNumberWith12diigits() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        UserDto userDto = defaultUserDto();
        PhoneDto phoneDto = userDto.getPhones().toArray(new PhoneDto[userDto.getPhones().size()])[1];
        userDto.getPhones().remove(phoneDto);
        phoneDto.setPhone(phoneDto.getPhone() + "0");
        userDto.getPhones().add(phoneDto);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Each phone number should not be null")
    public void testPhoneNumberIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        UserDto userDto = defaultUserDto();
        PhoneDto phoneDto = userDto.getPhones().toArray(new PhoneDto[userDto.getPhones().size()])[1];
        userDto.getPhones().remove(phoneDto);
        phoneDto.setPhone(null);
        userDto.getPhones().add(phoneDto);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Any phone should not be null")
    public void testPhoneIsNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        UserDto userDto = defaultUserDto();
        PhoneDto phoneDto = userDto.getPhones().toArray(new PhoneDto[userDto.getPhones().size()])[1];
        userDto.getPhones().remove(phoneDto);
        userDto.getPhones().add(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Phones should not be null")
    public void testPhonesAreNull() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"phone\".";
        final String MESSAGE = "The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.";
        UserDto userDto = defaultUserDto();
        userDto.setPhones(null);
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("Username should be unique")
    public void testDuplicatedUsername() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field \"username\".";
        final String MESSAGE = "Username was already registered.";
        final String USERNAME = "OtherUsername";
        UserDto userDto = defaultUserDto();
        userDto.getLogin().setUsername(USERNAME);
        userDto.setCpf("04629933883");
        if (!loginRepository.existsByUsername(userDto.getLogin().getUsername())) {
            userRepository.save(ConverterUtil.from(userDto, UserEntity.class));
        }
        userDto = defaultUserDto();
        userDto.getLogin().setUsername(USERNAME);
        userDto.setCpf("02629938808");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }

    @Test
    @DisplayName("CPF should be unique")
    public void testDuplicatedCPF() {
        final int EXPECTED_SIZE = 1;
        final String CAUSE = "Field\"cpf\".";
        final String MESSAGE = "CPF was already registered.";
        final String CPF = "04629933883";
        UserDto userDto = defaultUserDto();
        userDto.setCpf(CPF);
        userDto.getLogin().setUsername("OtherUsername");
        if (!userRepository.existsByCpf(userDto.getCpf())) {
            userRepository.save(ConverterUtil.from(userDto, UserEntity.class));
        }
        userDto = defaultUserDto();
        userDto.setCpf(CPF);
        userDto.getLogin().setUsername("DiferrentUsername");
        final Set<ErrorException> ERROR_EXCEPTIONS = validationService.validate(userDto);
        Assertions.assertEquals(EXPECTED_SIZE, ERROR_EXCEPTIONS.size());
        Assertions.assertEquals(new ErrorException(CAUSE, MESSAGE), ERROR_EXCEPTIONS.toArray()[0]);
    }
}
 
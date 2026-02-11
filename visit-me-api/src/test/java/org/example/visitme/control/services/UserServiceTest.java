package org.example.visitme.control.services;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.utils.ConverterUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Test of UserSerivce")
public class UserServiceTest {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginRepository loginRepository;

    private LoginDto defaultLoginDto() {
        return new LoginDto("signUpUsername", "Pa$$w0rd", "What is a question?", "This is answer.");
    }

    private Set<PhoneDto> defaultPhoneDtos() {
        Set<PhoneDto> phoneDtos = new HashSet<>();
        phoneDtos.add(new PhoneDto("0143218765"));
        phoneDtos.add(new PhoneDto("01912348765"));
        return phoneDtos;
    }

    private UserDto defaultUserDto() {
        final String fullname = "fullname";
        final String cpf = "04629933883";
        final String email = "a_user.name@domain.com";
        return new UserDto(defaultLoginDto(), fullname, cpf, email, defaultPhoneDtos());
    } 

    @Test
    @DisplayName("Test sign out with unregistered token")
    public void testSignOutWithUnregisteredToken() {
        final String token;
        final String username = "OtherUsername";
        UserDto userDto = defaultUserDto();
        userDto.setCpf("04629937870");
        userDto.getLogin().setUsername(username);
        if (!loginRepository.existsByUsername(username)) {
            homeService.signUp(ConverterUtil.from(userDto, UserDto.class));
        }
        token = "Bearer ".concat(homeService.signIn(userDto.getLogin()));
        Assertions.assertTrue(userService.signOut(token) > -1);
    }

    @Test
    @DisplayName("Test sign out with registered token")
    public void testSignOutWithRegisteredToken() {
        final String cause = "Header \"Authorization\".";
        final String message = "Token invalid. You must to sign in.";
        final ErrorException errorException = new ErrorException(cause, message);
        final String token;
        final String username = "OtherUsername";
        UserDto userDto = defaultUserDto();
        userDto.setCpf("04629937870");
        userDto.getLogin().setUsername(username);
        if (!loginRepository.existsByUsername(username)) {
            homeService.signUp(ConverterUtil.from(userDto, UserDto.class));
        }
        token = "Bearer ".concat(homeService.signIn(userDto.getLogin()));
        userService.signOut(token);
        try {
            userService.signOut(token);
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(errorException, exception.getError());
        }
    }
}

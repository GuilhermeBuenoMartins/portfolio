package org.example.visitme.control.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
        String fullname = "fullname";
        String cpf = "04629933883";
        String email = "a_user.name@domain.com";
        return new UserDto(defaultLoginDto(), fullname, cpf, email, defaultPhoneDtos());
    } 

    private String[] getCpfs() {
        return new String[]{
            "17510305047",  // CPF 1
            "15683190029",  // CPF 2
            "26905091057",  // CPF 3
            "45226917007",  // CPF 4
            "05234522091",  // CPF 5
            "59956949019"}; // CPF 6
    }
    
    private String[] getUsernames() {
        return new String[] {
            "aUsername_01",
            "aUsername_02",
            "aUsername_03",
            "aUsername_04",
            "aUsername_05",
            "aUsername_06"};
    }

    private String[] getFullnames() {
        return new String[] {
            "a Fullname 01",
            "a Fullname 02",
            "a Fullname 03",
            "a Fullname 04",
            "a Fullname 05",
            "a Fullname 06"};
    }

    @Test
    @DisplayName("Test sign out with unregistered token")
    public void testSignOutWithUnregisteredToken() {
        final String cause = "Header \"Authorization\".";
        final String message = "Token invalid. You must to sign in.";
        final String token;
        final String username = "OtherUsername";
        UserDto userDto = defaultUserDto();
        userDto.setCpf("04629937870");
        userDto.getLogin().setUsername(username);
        if (!loginRepository.existsByUsername(username)) {
            homeService.signUp(ConverterUtil.from(userDto, UserDto.class));
        }
        token = "Bearer ".concat(homeService.signIn(userDto.getLogin()));
        try {
            userService.signOut(token.concat("invalid"));
        } catch (AuthenticationException exception) {
            Assertions.assertEquals(new ErrorException(cause, message), exception.getError());
        }
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

    @Test
    @DisplayName("Test list users")
    public void testListUsers() {
        final String username = "aUsername_01";
        final int pageSize = 6;
        final int pageNumber = 0;
        signUpUserList();
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        Page<UserDto> page = userService.searchByFullname(null, pageable);
        Assertions.assertTrue(page.hasContent());
        Assertions.assertTrue(page.getTotalPages() >= 1);
        Assertions.assertEquals(pageNumber, page.getNumber());
        Assertions.assertEquals(pageSize, page.getSize());
        List<UserDto> pageContent = (List<UserDto>) page.getContent();
        Optional<UserDto> userDto = pageContent.stream().filter(
            p -> p.getLogin().getUsername().equals(username)).findFirst();
        Assertions.assertTrue(userDto.isPresent());
    }

    @Test
    @DisplayName("Test list users")
    public void testListUsersContains() {
        final String username = "aUsername_03";
        final String fullName = "03";
        final int pageSize = 5;
        final int pageNumber = 0;
        signUpUserList();
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        Page<UserDto> page = userService.searchByFullname(fullName, pageable);
        Assertions.assertTrue(page.hasContent());
        Assertions.assertTrue(page.getTotalPages() == 1);
        Assertions.assertEquals(pageNumber, page.getNumber());
        Assertions.assertEquals(pageSize, page.getSize());
        List<UserDto> pageContent = (List<UserDto>) page.getContent();
        Optional<UserDto> userDto = pageContent.stream().filter(
            p -> p.getLogin().getUsername().equals(username)).findFirst();
        Assertions.assertTrue(userDto.isPresent());
    }

    private void signUpUserList() {
        String[] fullnames = getFullnames();
        String[] usernames = getUsernames();
        String[] cpfs = getCpfs();
        for (int i = 0; i < fullnames.length; i++) {
            if (!loginRepository.existsByUsername(usernames[i])) {
                UserDto userDto = defaultUserDto();
                userDto.setFullName(fullnames[i]);
                userDto.setCpf(cpfs[i]);
                userDto.getLogin().setUsername(usernames[i]);
                homeService.signUp(userDto);
            }
        }
    }
}

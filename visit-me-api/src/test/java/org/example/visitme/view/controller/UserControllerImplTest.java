package org.example.visitme.view.controller;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.HomeService;
import org.example.visitme.control.services.UserService;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.utils.ConverterUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test of UserControllerImpl")
public class UserControllerImplTest {

    private static final String SIGN_OUT_ENDPOINT = "/v1/users/sign-out";

    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("User signs out successfully")
    public void testSignOut() throws Exception {
        final String token;
        final String username = "AnotherUsername";
        UserDto userDto = defaultUserDto();
        userDto.setCpf("61527282074");
        userDto.getLogin().setUsername(username);
        if (!loginRepository.existsByUsername(username)) {
            homeService.signUp(ConverterUtil.from(userDto, UserDto.class));
        }
        token = "Bearer ".concat(homeService.signIn(userDto.getLogin()));
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().isOk();
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.OK.toString()));
        final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.message", Matchers.is("You are signed out from system."));
        final ResultMatcher dataMatcher = MockMvcResultMatchers.jsonPath("$.data",
                Matchers.is(true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SIGN_OUT_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpectAll(
                statusCodeMatcher, timestampMatcher, statusMatcher, messageMatcher, dataMatcher)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs out without authentication token")
    public void testSignOutWithoutAuthenticationToken() throws Exception {
        final String cause = "Header \"Authorization\".";
        final String message = "Token invalid. You must to sign in.";
        final String username = "OtherUsername";
        UserDto userDto = defaultUserDto();
        userDto.setCpf("04629937870");
        userDto.getLogin().setUsername(username);
        if (!loginRepository.existsByUsername(username)) {
            homeService.signUp(ConverterUtil.from(userDto, UserDto.class));
        }
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
        final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
        final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(message));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SIGN_OUT_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs out with invalid authentication token")
    public void testSignOutWithInvalidAuthenticationToken() throws Exception {
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
        userService.signOut(token);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
        final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
        final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(message));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SIGN_OUT_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.header("Authorization", token);
        mockMvc.perform(requestBuilder)
                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs out with expired authentication token")
    public void testSignOutWithExpiredAuthenticationToken() throws Exception {
        final String cause = "Header \"Authorization\".";
        final String message = "Token invalid. You must to sign in.";
        final String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3NzA1Nzk2NjcsInN1YiI6ImEudXNlcm5hbWUiLCJleHAiOjE3NzA1ODAyNjd9.CPMEu3_CXJAaeXVAnQke9pzB9kFbsr2vi9Vw9pGhG32rQZ2HrsLrCryqiH4zHPRYvWMaoWNNCQKqWdZzF9PWSA";
        final String username = "OtherUsername";
        UserDto userDto = defaultUserDto();
        userDto.setCpf("04629937870");
        userDto.getLogin().setUsername(username);
        if (!loginRepository.existsByUsername(username)) {
            homeService.signUp(ConverterUtil.from(userDto, UserDto.class));
        }
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
        final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
        final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(message));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SIGN_OUT_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.header("Authorization", token);
        mockMvc.perform(requestBuilder)
                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User list users without fullname")
    public void testListUsersWithoutFullname() throws Exception {
        final LoginDto loginDto = new LoginDto("aUsername_01", "Pa$$w0rd", null, null);
        final int pageSize = 5;
        final int pageNumber = 0;
        signUpUserList();
        final String token = homeService.signIn(loginDto);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().isOk();
        final ResultMatcher contentTypeMatcher = MockMvcResultMatchers.content().contentType(MediaType. APPLICATION_JSON_VALUE);
        final ResultMatcher pageNumberMatcher = MockMvcResultMatchers.jsonPath("$.number", Matchers.is(pageNumber));
        final ResultMatcher pageSizeMatcher = MockMvcResultMatchers.jsonPath("$.size", Matchers.is(pageSize));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/users");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.header("Authorization", "Bearer ".concat(token));
        mockMvc.perform(requestBuilder)
                .andExpectAll(statusCodeMatcher, contentTypeMatcher, pageNumberMatcher, pageSizeMatcher)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User list users with fullname")
    public void testListUsersWithFullname() throws Exception {
        final LoginDto loginDto = new LoginDto("aUsername_01", "Pa$$w0rd", null, null);
        final String fullName = "a Full";
        final int totalElements = 6;
        final int totalPages = 2;
        final int pageSize = 5;
        final int pageNumber = 1;
        signUpUserList();
        final String token = homeService.signIn(loginDto);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().isOk();
        final ResultMatcher contentTypeMatcher = MockMvcResultMatchers.content().contentType(MediaType. APPLICATION_JSON_VALUE);
        final ResultMatcher totalContentMatcher = MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(1));
        final ResultMatcher totalElementsMatcher = MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(totalElements));
        final ResultMatcher totalPagesMatcher = MockMvcResultMatchers.jsonPath("$.totalPages", Matchers.is(totalPages));
        final ResultMatcher pageNumberMatcher = MockMvcResultMatchers.jsonPath("$.number", Matchers.is(pageNumber));
        final ResultMatcher pageSizeMatcher = MockMvcResultMatchers.jsonPath("$.size", Matchers.is(pageSize));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/users");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.header("Authorization", "Bearer ".concat(token));
        requestBuilder.param("fullname", fullName);
        requestBuilder.param("page", String.valueOf(pageNumber));
        mockMvc.perform(requestBuilder)
                .andExpectAll(statusCodeMatcher, contentTypeMatcher, totalContentMatcher, totalElementsMatcher, 
                    totalPagesMatcher, pageNumberMatcher, pageSizeMatcher)
                .andDo(MockMvcResultHandlers.print());
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
}

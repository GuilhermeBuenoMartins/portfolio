package org.example.visitme.view.controller;

import java.util.ArrayList;
import java.util.List;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.UserService;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.view.requests.LoginRequest;
import org.example.visitme.view.requests.PhoneRequest;
import org.example.visitme.view.requests.SignInRequest;
import org.example.visitme.view.requests.UserRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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

import tools.jackson.databind.json.JsonMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test of UserController")
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    private UserRequest getDefaultUserRequest() {
        final List<PhoneRequest> phoneRequests = List.of(new PhoneRequest(null, "11964530987"), new PhoneRequest(null, "1142789801"));
        final LoginRequest loginRequest = new LoginRequest("other_username", "oth3rP*ssw0rd", "What is other question?", "This is second answer");
        return new UserRequest(loginRequest, "Other fullname", "80976868059", "other_username@domain.com", phoneRequests);
    }

    public UserDto getAUserDto() {
        List<PhoneDto> phoneDtos = List.of(new PhoneDto("11964530987"), new PhoneDto( "1142789801"));
        LoginDto loginDto = new LoginDto("other_username", "oth3rP*ssw0rd", "What is other question?", "This is second answer");
        UserDto userDto = new UserDto(loginDto, "Other fullname", "80976868059", "other_username@domain.com", phoneDtos);
        return userDto;
    }

    @Test
    @DisplayName("User should be registered")
    public void testUserRegistration() throws Exception {
        final String MESSAGE = "You were signed up successfully.";
        UserRequest userRequest = new UserRequest();
        LoginRequest loginRequest = new LoginRequest();
        List<PhoneRequest> phoneRequests = new ArrayList<>();
        loginRequest.setUsername("a.username");
        loginRequest.setPassword("aP*ssw0rd");
        loginRequest.setRecoveryPasswordQuestion("What is the question?");
        loginRequest.setRecoveryPasswordAnswer("This is the question");
        phoneRequests.add(new PhoneRequest(null, "11964530987"));
        phoneRequests.add(new PhoneRequest(null, "1142789801"));
        userRequest.setLogin(loginRequest);
        userRequest.setFullName("Complete name");
        userRequest.setCpf("09853843013");
        userRequest.setEmail("a.username@domain.com");
        userRequest.setPhones(phoneRequests);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(userRequest)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(MESSAGE))
            .andDo(MockMvcResultHandlers.print());
        Assertions.assertTrue(repository.existsByCpf((String) userRequest.getCpf()));
    }

    @Test
    @DisplayName("Request values should not be null")
    public void testRequestNull() throws Exception {
        final String ENDPOINT = "/v1/users/sign-up";
        UserRequest userRequest = new UserRequest();
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(userRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(ENDPOINT))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(8)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("fullName")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is("The field must have: minimum of 8 characters; maximum of 96 characters.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[1].cause", Matchers.is("password")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[1].message", Matchers.is("The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[2].cause", Matchers.is("phones")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[2].message", Matchers.is("The field must have: minimum of 1 phone number; maximum of 2 phone numbers; each phone must contain a minimum of 10 digits; each phone must contain a maximum of 11 digits.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[3].cause", Matchers.is("username")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[3].message", Matchers.is("The field must have: minimum of 8 characters; maximum of 64 characters; no spaces.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[4].cause", Matchers.is("recoveryPasswordAnswer")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[4].message", Matchers.is("The field must have: minimum of 8 characters; maximum of 32 characters.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[5].cause", Matchers.is("cpf")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[5].message", Matchers.is("The field must have: exactly 11 digits; no special characters.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[6].cause", Matchers.is("recoveryPasswordQuestion")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[6].message", Matchers.is("The field must have: minimum of 8 characters; maximum of 256 characters.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[7].cause", Matchers.is("email")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[7].message", Matchers.is("The field must have a maximum of 64 characters.")))
            .andDo(MockMvcResultHandlers.print());    
    }

    @Test
    @DisplayName("Register should not be duplicated")
    public void testDuplicatedRequest() throws Exception {
        UserRequest userRequest = new UserRequest();
        LoginRequest loginRequest = new LoginRequest();
        List<PhoneRequest> phoneRequests = new ArrayList<>();
        loginRequest.setUsername("a.username");
        loginRequest.setPassword("aP*ssw0rd");
        loginRequest.setRecoveryPasswordQuestion("What is the question?");
        loginRequest.setRecoveryPasswordAnswer("This is the question");
        phoneRequests.add(new PhoneRequest(null, "11964530987"));
        phoneRequests.add(new PhoneRequest(null, "1142789801"));
        userRequest.setLogin(loginRequest);
        userRequest.setFullName("Complete name");
        userRequest.setCpf("09853843013");
        userRequest.setEmail("a.username@domain.com");
        userRequest.setPhones(phoneRequests);
        if (!repository.existsByCpf((String) userRequest.getCpf())) {
            UserDto dto = (UserDto) ConverterUtil.from(userRequest, UserDto.class);
            userService.insert(dto);
        }
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(userRequest)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("username")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is("Username was already registered.")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[1].cause", Matchers.is("cpf")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.messages[1].message", Matchers.is("CPF was already registered.")))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Sistem should return a token")
    public void testSignIn() throws Exception {
        final UserRequest USER_REQUEST = getDefaultUserRequest();
        if (!userService.hasCpf((String) USER_REQUEST.getCpf())) { userService.insert(ConverterUtil.from(USER_REQUEST, UserDto.class)); }
        final SignInRequest SIGN_IN_REQUEST = new SignInRequest((String) USER_REQUEST.getLogin().getUsername(), (String) USER_REQUEST.getLogin().getPassword());
        final String REQUEST_BODY = new JsonMapper().writeValueAsString(SIGN_IN_REQUEST);
        final MockHttpServletRequestBuilder REQUEST_BUILDER = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
        final ResultMatcher DATA_MATCHER = MockMvcResultMatchers.jsonPath("$.data", Matchers.hasLength(194));
        mockMvc.perform(REQUEST_BUILDER).andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, DATA_MATCHER).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Username should not be incorrect")
    public void testSignInWithIncorrectUsername() throws Exception {
        final String MESSAGE_ERROR = "Username or password are incorrect";
        final UserRequest USER_REQUEST = getDefaultUserRequest();
        if (!userService.hasCpf((String) USER_REQUEST.getCpf())) { userService.insert(ConverterUtil.from(USER_REQUEST, UserDto.class)); }
        final SignInRequest SIGN_IN_REQUEST = new SignInRequest("incorrectUsername", (String) USER_REQUEST.getLogin().getPassword());
        final String REQUEST_BODY = new JsonMapper().writeValueAsString(SIGN_IN_REQUEST);
        final MockHttpServletRequestBuilder REQUEST_BUILDER = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("signIn"));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(MESSAGE_ERROR));
        mockMvc.perform(REQUEST_BUILDER).andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Password should not be incorrect")
    public void testSignInWithIncorrectPassword() throws Exception {
        final String MESSAGE_ERROR = "Username or password are incorrect";
        final UserRequest USER_REQUEST = getDefaultUserRequest();
        if (!userService.hasCpf((String) USER_REQUEST.getCpf())) { userService.insert(ConverterUtil.from(USER_REQUEST, UserDto.class)); }
        final SignInRequest SIGN_IN_REQUEST = new SignInRequest((String) USER_REQUEST.getLogin().getUsername(), "inc0rrectP@ssword");
        final String REQUEST_BODY = new JsonMapper().writeValueAsString(SIGN_IN_REQUEST);
        final MockHttpServletRequestBuilder REQUEST_BUILDER = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("signIn"));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(MESSAGE_ERROR));
        mockMvc.perform(REQUEST_BUILDER).andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Username should not be null")
    public void testSignInWithNullUsername() throws Exception {
        final String MESSAGE_ERROR = "Username cannot be null or empty.";
        final UserRequest USER_REQUEST = getDefaultUserRequest();
        final SignInRequest SIGN_IN_REQUEST = new SignInRequest(null, (String) USER_REQUEST.getLogin().getPassword());
        final String REQUEST_BODY = new JsonMapper().writeValueAsString(SIGN_IN_REQUEST);
        final MockHttpServletRequestBuilder REQUEST_BUILDER = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("username"));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(MESSAGE_ERROR));
        mockMvc.perform(REQUEST_BUILDER).andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Username should not be blank")
    public void testSignInWithBlankUsername() throws Exception {
        final String MESSAGE_ERROR = "Username cannot be null or empty.";
        final UserRequest USER_REQUEST = getDefaultUserRequest();
        final SignInRequest SIGN_IN_REQUEST = new SignInRequest("     ", (String) USER_REQUEST.getLogin().getPassword());
        final String REQUEST_BODY = new JsonMapper().writeValueAsString(SIGN_IN_REQUEST);
        final MockHttpServletRequestBuilder REQUEST_BUILDER = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("username"));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(MESSAGE_ERROR));
        mockMvc.perform(REQUEST_BUILDER).andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Password should not be null")
    public void testSignInWithNullPassword() throws Exception {
        final String MESSAGE_ERROR = "Password cannot be null or empty.";
        final UserRequest USER_REQUEST = getDefaultUserRequest();
        final SignInRequest SIGN_IN_REQUEST = new SignInRequest((String) USER_REQUEST.getLogin().getUsername(), null);
        final String REQUEST_BODY = new JsonMapper().writeValueAsString(SIGN_IN_REQUEST);
        final MockHttpServletRequestBuilder REQUEST_BUILDER = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("password"));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(MESSAGE_ERROR));
        mockMvc.perform(REQUEST_BUILDER).andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Password should not be blank")
    public void testSignInWithBlankPassword() throws Exception {
        final String MESSAGE_ERROR = "Password cannot be null or empty.";
        final UserRequest USER_REQUEST = getDefaultUserRequest();
        final SignInRequest SIGN_IN_REQUEST = new SignInRequest((String) USER_REQUEST.getLogin().getUsername(), "     ");
        final String REQUEST_BODY = new JsonMapper().writeValueAsString(SIGN_IN_REQUEST);
        final MockHttpServletRequestBuilder REQUEST_BUILDER = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is("password"));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(MESSAGE_ERROR));
        mockMvc.perform(REQUEST_BUILDER).andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER).andDo(MockMvcResultHandlers.print());
    }
}
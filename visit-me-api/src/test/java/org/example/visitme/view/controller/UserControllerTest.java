package org.example.visitme.view.controller;

import java.util.ArrayList;
import java.util.List;

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
        final UserRequest userRequest = getDefaultUserRequest();
        final SignInRequest signInRequest = new SignInRequest((String) userRequest.getLogin().getUsername(), (String) userRequest.getLogin().getPassword());
        final String requestBody = new JsonMapper().writeValueAsString(signInRequest);
        if (!userService.hasCpf((String) userRequest.getCpf())) {
            userService.insert(ConverterUtil.from(userRequest, UserDto.class));
        }
        final MockHttpServletRequestBuilder RequestBuilder = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(requestBody);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
        mockMvc.perform(RequestBuilder).andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Username should not be incorrect")
    public void testSignInWithIncorrectUsername() throws Exception {
        final UserRequest userRequest = getDefaultUserRequest();
        final SignInRequest signInRequest = new SignInRequest("incorrectUsername", (String) userRequest.getLogin().getPassword());
        final String requestBody = new JsonMapper().writeValueAsString(signInRequest);
        if (!userService.hasCpf((String) userRequest.getCpf())) {
            userService.insert(ConverterUtil.from(userRequest, UserDto.class));
        }
        final MockHttpServletRequestBuilder RequestBuilder = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(requestBody);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
        mockMvc.perform(RequestBuilder).andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Password should not be incorrect")
    public void testSignInWithIncorrectPassword() throws Exception {
        final UserRequest userRequest = getDefaultUserRequest();
        final SignInRequest signInRequest = new SignInRequest((String) userRequest.getLogin().getUsername(), "inc0rrectP@ssword");
        final String requestBody = new JsonMapper().writeValueAsString(signInRequest);
        if (!userService.hasCpf((String) userRequest.getCpf())) {
            userService.insert(ConverterUtil.from(userRequest, UserDto.class));
        }
        final MockHttpServletRequestBuilder RequestBuilder = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(requestBody);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
        mockMvc.perform(RequestBuilder).andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Username should not be null")
    public void testSignInWithNullUsername() throws Exception {
        final UserRequest userRequest = getDefaultUserRequest();
        final SignInRequest signInRequest = new SignInRequest(null, (String) userRequest.getLogin().getPassword());
        final String requestBody = new JsonMapper().writeValueAsString(signInRequest);
        if (!userService.hasCpf((String) userRequest.getCpf())) {
            userService.insert(ConverterUtil.from(userRequest, UserDto.class));
        }
        final MockHttpServletRequestBuilder RequestBuilder = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(requestBody);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
        mockMvc.perform(RequestBuilder).andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Password should not be null")
    public void testSignInWithNullPassword() throws Exception {
        final UserRequest userRequest = getDefaultUserRequest();
        final SignInRequest signInRequest = new SignInRequest((String) userRequest.getLogin().getUsername(), null);
        final String requestBody = new JsonMapper().writeValueAsString(signInRequest);
        if (!userService.hasCpf((String) userRequest.getCpf())) {
            userService.insert(ConverterUtil.from(userRequest, UserDto.class));
        }
        final MockHttpServletRequestBuilder RequestBuilder = MockMvcRequestBuilders.post("/v1/users/sign-in").contentType(MediaType.APPLICATION_JSON).content(requestBody);
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
        mockMvc.perform(RequestBuilder).andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher).andDo(MockMvcResultHandlers.print());
    }
}
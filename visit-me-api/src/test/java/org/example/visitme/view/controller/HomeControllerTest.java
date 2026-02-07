package org.example.visitme.view.controller;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.HomeService;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.view.requests.LoginRequest;
import org.example.visitme.view.requests.PhoneRequest;
import org.example.visitme.view.requests.SignInRequest;
import org.example.visitme.view.requests.SignUpRequest;
import org.example.visitme.view.responses.HealthResponse;
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

import com.fasterxml.jackson.databind.json.JsonMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test of Home Controller")
public class HomeControllerTest {

    private final String SIGN_UP_ENDPOINT = "/v1/home/sign-up";

    private final String SIGN_IN_ENDPOINT = "/v1/home/sign-in";

    @Autowired
    private HomeService homeService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private LoginRequest defaultLoginRequest() {
        return new LoginRequest("signUpUsername", "Pa$$w0rd", "What is a question?", "This is answer.");
    }

    private Set<PhoneRequest> defaultPhoneRequests() {
        Set<PhoneRequest> phoneRequests = new HashSet<>();
        phoneRequests.add(new PhoneRequest("0143218765"));
        phoneRequests.add(new PhoneRequest("01912348765"));
        return phoneRequests;
    }

    private SignUpRequest defaultSignUpRequest() {
        final String FULLNAME = "fullname";
        final String CPF = "04629933883";
        final String EMAIL = "a_user.name@domain.com";
        return new SignUpRequest(defaultLoginRequest(), FULLNAME, CPF, EMAIL, defaultPhoneRequests());
    }

    private SignInRequest defaulSignInRequest() {
        return new SignInRequest("OtherUsername", "Pa$$w0rd");
    }

    @Test
    @DisplayName("Check health")
    public void testHealth() throws Exception {
        final HealthResponse RESPONSE = new HealthResponse("UP");
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().isOk();
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.OK.toString()));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.message", Matchers.is("It's up."));
        final ResultMatcher DATA_MATCHER = MockMvcResultMatchers.jsonPath("$.data.status",
                Matchers.is(RESPONSE.getStatus()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/home/health");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpectAll(
                STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, MESSAGE_MATCHER, DATA_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User should be registered")
    public void testUserRegistration() throws Exception {
        final String MESSAGE = "You were signed up successfully.";
        SignUpRequest signUpRequest = defaultSignUpRequest();
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().isCreated();
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.CREATED.toString()));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.message", Matchers.is(MESSAGE));
        final ResultMatcher DATA_MATCHER = MockMvcResultMatchers.jsonPath("$.data", Matchers.notNullValue());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_UP_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(signUpRequest));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, MESSAGE_MATCHER, DATA_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User should not register duplicated CPF")
    public void testUserRegistrationWithDuplicatedCpf() throws Exception {
        final String CAUSE = "Field\"cpf\".";
        final String MESSAGE = "CPF was already registered.";
        final String CPF = "04629936807";
        SignUpRequest signUpRequest = defaultSignUpRequest();
        signUpRequest.getLogin().setUsername("CpfUsername");
        signUpRequest.setCpf(CPF);
        if (!userRepository.existsByCpf(CPF)) {
            homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
        }
        signUpRequest.getLogin().setUsername("CpfUsername2");
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(CAUSE));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(MESSAGE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_UP_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(signUpRequest));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User should not register duplicated Username")
    public void testUserRegistrationWithDuplicatedUsername() throws Exception {
        final String CAUSE = "Field \"username\".";
        final String MESSAGE = "Username was already registered.";
        final String USERNAME = "OtherUsername";
        SignUpRequest signUpRequest = defaultSignUpRequest();
        signUpRequest.setCpf("04629937870");
        signUpRequest.getLogin().setUsername(USERNAME);
        if (!loginRepository.existsByUsername(USERNAME)) {
            homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
        }
        signUpRequest.setCpf("04629938842");
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(CAUSE));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(MESSAGE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_UP_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(signUpRequest));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs in with valid login")
    public void testSignInWithValidLogin() throws Exception {
        final String USERNAME = "OtherUsername";
        final SignInRequest SIGN_IN_REQUEST = defaulSignInRequest();
        SignUpRequest signUpRequest = defaultSignUpRequest();
        signUpRequest.setCpf("04629937870");
        signUpRequest.getLogin().setUsername(USERNAME);
        if (!loginRepository.existsByUsername(USERNAME)) {
            homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
        }
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(SIGN_IN_REQUEST));
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.OK.toString()));
        final ResultMatcher DATA_MATCHER = MockMvcResultMatchers.jsonPath("$.data", Matchers.hasLength(192));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, DATA_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs in with null username")
    public void testSignInWithNullUsername() throws Exception {
        final String CAUSE = "Field \"username\"";
        final String MESSAGE = "Username cannot be null or empty.";
        final String USERNAME = "OtherUsername";
        SignInRequest signInRequest = defaulSignInRequest();
        SignUpRequest signUpRequest = defaultSignUpRequest();
        signUpRequest.setCpf("04629937870");
        signUpRequest.getLogin().setUsername(USERNAME);
        if (!loginRepository.existsByUsername(USERNAME)) {
            homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
        }
        signInRequest.setUsername(null);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(CAUSE));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(MESSAGE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs in with null password")
    public void testSignInWithNullPasswrod() throws Exception {
        final String CAUSE = "Field \"password\"";
        final String MESSAGE = "Password cannot be null or empty.";
        final String USERNAME = "OtherUsername";
        SignInRequest signInRequest = defaulSignInRequest();
        SignUpRequest signUpRequest = defaultSignUpRequest();
        signUpRequest.setCpf("04629937870");
        signUpRequest.getLogin().setUsername(USERNAME);
        if (!loginRepository.existsByUsername(USERNAME)) {
            homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
        }
        signInRequest.setPassword(null);
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.BAD_REQUEST.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(CAUSE));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(MESSAGE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs in with incorrect username")
    public void testSignInWithIncorrectUsername() throws Exception {
        final String CAUSE = "Fields \"username\" or \"password\".";
        final String MESSAGE = "Username or password are incorrect.";
        final String USERNAME = "OtherUsername";
        SignInRequest signInRequest = defaulSignInRequest();
        SignUpRequest signUpRequest = defaultSignUpRequest();
        signUpRequest.setCpf("04629937870");
        signUpRequest.getLogin().setUsername(USERNAME);
        if (!loginRepository.existsByUsername(USERNAME)) {
            homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
        }
        signInRequest.setUsername("IncorrectUsername");
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(CAUSE));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(MESSAGE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("User signs in with incorrect password")
    public void testSignInWithIncorrectPasswrod() throws Exception {
        final String CAUSE = "Fields \"username\" or \"password\".";
        final String MESSAGE = "Username or password are incorrect.";
        final String USERNAME = "OtherUsername";
        SignInRequest signInRequest = defaulSignInRequest();
        SignUpRequest signUpRequest = defaultSignUpRequest();
        signUpRequest.setCpf("04629937870");
        signUpRequest.getLogin().setUsername(USERNAME);
        if (!loginRepository.existsByUsername(USERNAME)) {
            homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
        }
        signInRequest.setPassword("inco#ectPa$$w0rd");
        final ResultMatcher STATUS_CODE_MATCHER = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
        final ResultMatcher TIMESTAMP_MATCHER = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher STATUS_MATCHER = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
        final ResultMatcher CAUSE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(CAUSE));
        final ResultMatcher MESSAGE_MATCHER = MockMvcResultMatchers.jsonPath("$.messages[0].message",
                Matchers.is(MESSAGE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
        mockMvc.perform(requestBuilder)
                .andExpectAll(STATUS_CODE_MATCHER, TIMESTAMP_MATCHER, STATUS_MATCHER, CAUSE_MATCHER, MESSAGE_MATCHER)
                .andDo(MockMvcResultHandlers.print());
    }
}

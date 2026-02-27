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

import com.fasterxml.jackson.databind.json.JsonMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test of HomeControllerImpl")
public class HomeControllerImplTest {

        private static final String SIGN_UP_ENDPOINT = "/v1/home/sign-up";

        private static final String SIGN_IN_ENDPOINT = "/v1/home/sign-in";

        private static final String RECOVERY_ENDPOINT = "/v1/home/recovery/{cpf}";
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
                final String fullName = "fullname";
                final String cpf = "04629933883";
                final String email = "a_user.name@domain.com";
                return new SignUpRequest(defaultLoginRequest(), fullName, cpf, email, defaultPhoneRequests());
        }

        private SignInRequest defaultSignInRequest() {
                return new SignInRequest("OtherUsername", "Pa$$w0rd");
        }

        @Test
        @DisplayName("Check health")
        public void testHealth() throws Exception {
                final HealthResponse RESPONSE = new HealthResponse("UP");
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().isOk();
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.message", Matchers.is("It's up."));
                final ResultMatcher dataMatcher = MockMvcResultMatchers.jsonPath("$.data.status", Matchers.is(RESPONSE.getStatus()));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/home/health");
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(requestBuilder).andExpectAll(
                                statusCodeMatcher, timestampMatcher, statusMatcher, messageMatcher, dataMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should be registered")
        public void testUserRegistration() throws Exception {
                final String message = "You were signed up successfully.";
                SignUpRequest signUpRequest = defaultSignUpRequest();
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().isCreated();
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.CREATED.toString()));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message));
                final ResultMatcher dataMatcher = MockMvcResultMatchers.jsonPath("$.data", Matchers.notNullValue());
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_UP_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signUpRequest));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, messageMatcher, dataMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not register duplicated CPF")
        public void testUserRegistrationWithDuplicatedCpf() throws Exception {
                final String cause = "Field\"cpf\".";
                final String message = "CPF was already registered.";
                final String cpf = "04629936807";
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.getLogin().setUsername("CpfUsername");
                signUpRequest.setCpf(cpf);
                if (!userRepository.existsByCpf(cpf)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signUpRequest.getLogin().setUsername("CpfUsername2");
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status() .is(HttpStatus.BAD_REQUEST.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_UP_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signUpRequest));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not register duplicated Username")
        public void testUserRegistrationWithDuplicatedUsername() throws Exception {
                final String cause = "Field \"username\".";
                final String message = "Username was already registered.";
                final String username = "OtherUsername";
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf("04629937870");
                signUpRequest.getLogin().setUsername(username);
                if (!loginRepository.existsByUsername(username)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signUpRequest.setCpf("04629938842");
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status() .is(HttpStatus.BAD_REQUEST.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_UP_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signUpRequest));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User signs in with valid login")
        public void testSignInWithValidLogin() throws Exception {
                final String username = "OtherUsername";
                final SignInRequest SIGN_IN_REQUEST = defaultSignInRequest();
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf("04629936807");
                signUpRequest.getLogin().setUsername(username);
                if (!loginRepository.existsByUsername(username)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(SIGN_IN_REQUEST));
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
                final ResultMatcher dataMatcher = MockMvcResultMatchers.jsonPath("$.data", Matchers.hasLength(184));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, dataMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User signs in with null username")
        public void testSignInWithNullUsername() throws Exception {
                final String cause = "Field \"username\"";
                final String message = "Username cannot be null or empty.";
                final String username = "OtherUsername";
                SignInRequest signInRequest = defaultSignInRequest();
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf("04629937870");
                signUpRequest.getLogin().setUsername(username);
                if (!loginRepository.existsByUsername(username)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signInRequest.setUsername(null);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status() .is(HttpStatus.BAD_REQUEST.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User signs in with null password")
        public void testSignInWithNullPasswrod() throws Exception {
                final String cause = "Field \"password\"";
                final String message = "Password cannot be null or empty.";
                final String username = "OtherUsername";
                SignInRequest signInRequest = defaultSignInRequest();
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf("04629937870");
                signUpRequest.getLogin().setUsername(username);
                if (!loginRepository.existsByUsername(username)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signInRequest.setPassword(null);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status() .is(HttpStatus.BAD_REQUEST.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User signs in with incorrect username")
        public void testSignInWithIncorrectUsername() throws Exception {
                final String cause = "Fields \"username\" or \"password\".";
                final String message = "Username or password are incorrect.";
                final String username = "OtherUsername";
                SignInRequest signInRequest = defaultSignInRequest();
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf("04629937870");
                signUpRequest.getLogin().setUsername(username);
                if (!loginRepository.existsByUsername(username)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signInRequest.setUsername("IncorrectUsername");
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status() .is(HttpStatus.UNAUTHORIZED.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User signs in with incorrect password")
        public void testSignInWithIncorrectPasswrod() throws Exception {
                final String cause = "Fields \"username\" or \"password\".";
                final String message = "Username or password are incorrect.";
                final String username = "OtherUsername";
                SignInRequest signInRequest = defaultSignInRequest();
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf("04629937870");
                signUpRequest.getLogin().setUsername(username);
                if (!loginRepository.existsByUsername(username)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signInRequest.setPassword("inco#ectPa$$w0rd");
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status() .is(HttpStatus.UNAUTHORIZED.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(SIGN_IN_ENDPOINT);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signInRequest));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should get recovery password question with valid CPF")
        public void testGetRecoveryPasswordQuestionWithValidCpf() throws Exception {
                final String cpf = "04629936807";
                final String QUESTION = "What is a question?";
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf(cpf);
                signUpRequest.getLogin().setUsername("OtherUsername");
                if (!userRepository.existsByCpf(cpf)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(RECOVERY_ENDPOINT,
                                cpf);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
                final ResultMatcher dataMatcher = MockMvcResultMatchers.jsonPath("$.data", Matchers.is(QUESTION));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, dataMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not get recovery password question with invalid CPF")
        public void testGetRecoveryPasswordQuestionWithInvalidCpf() throws Exception {
                final String cause = "Path \"CPF\".";
                final String message = "The field must have: exactly 11 digits; no special characters.";
                final String cpf = "04629933884";
                Assertions.assertFalse(userRepository.existsByCpf(cpf));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(RECOVERY_ENDPOINT,
                                cpf);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status() .is(HttpStatus.BAD_REQUEST.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher pathMatcher = MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/home/recovery/" + cpf));
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, pathMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not get recovery password question with nonexistent CPF")
        public void testGetRecoveryPasswordQuestionWithNonexistentCpf() throws Exception {
                final String cause = "Path \"CPF\".";
                final String message = "This CPF does not exist in the system. Please, sign up.";
                final String cpf = "68874221070";
                Assertions.assertFalse(userRepository.existsByCpf(cpf));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(RECOVERY_ENDPOINT,
                                cpf);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher pathMatcher = MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/home/recovery/" + cpf));
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.NOT_FOUND.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, pathMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should update password with valid CPF and valid request")
        public void testUpdatePasswordWithValidCpfAndValidRequest() throws Exception {
                final String newPassword = "N3wPa$$w0rd";
                final String cpf = "32349692051";
                final String message = "Your password was changed successfully! Please, sign in to system with the new password.";
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf(cpf);
                signUpRequest.getLogin().setUsername("updatePassword");
                if (!userRepository.existsByCpf(cpf)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signUpRequest.getLogin().setPassword(newPassword);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(RECOVERY_ENDPOINT, cpf);
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                requestBuilder.content(new JsonMapper().writeValueAsString(signUpRequest.getLogin()));
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.OK.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.OK.toString()));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message));
                final ResultMatcher dataUsernameMatcher = MockMvcResultMatchers.jsonPath("$.data.username", Matchers.is(signUpRequest.getLogin().getUsername()));
                final ResultMatcher dataPasswordMatcher = MockMvcResultMatchers.jsonPath("$.data.password", Matchers.notNullValue());
                final ResultMatcher dataQuestionMatcher = MockMvcResultMatchers.jsonPath("$.data.recoveryPasswordQuestion", Matchers.is(signUpRequest.getLogin().getRecoveryPasswordQuestion()));
                final ResultMatcher dataAnswerMatcher = MockMvcResultMatchers.jsonPath("$.data.recoveryPasswordAnswer", Matchers.notNullValue());
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, statusMatcher, messageMatcher, dataUsernameMatcher, dataPasswordMatcher, dataQuestionMatcher, dataAnswerMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not update password with nonexistent CPF")
        public void testUpdatePasswordWithNonexistentCpf() throws Exception {
                final String cause = "Path \"CPF\".";
                final String message = "This CPF does not exist in the system. Please, sign up.";
                final String newPassword = "n3wP@ssw0rd";
                final String cpf = "68874221070";
                LoginRequest loginRequest = defaultLoginRequest();
                loginRequest.setPassword(newPassword);
                Assertions.assertFalse(userRepository.existsByCpf(cpf));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(RECOVERY_ENDPOINT, cpf);
                requestBuilder.content(new JsonMapper().writeValueAsBytes(loginRequest));
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher pathMatcher = MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/home/recovery/" + cpf));
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.NOT_FOUND.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, pathMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not update password with invalid CPF")
        public void testUpdatePasswordWithInvalidCpf() throws Exception {
                final String cause = "Path \"CPF\".";
                final String message = "The field must have: exactly 11 digits; no special characters.";
                final String newPassword = "n3wP@ssw0rd";
                final String cpf = "68874221071";
                LoginRequest loginRequest = defaultLoginRequest();
                loginRequest.setPassword(newPassword);
                Assertions.assertFalse(userRepository.existsByCpf(cpf));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(RECOVERY_ENDPOINT, cpf);
                requestBuilder.content(new JsonMapper().writeValueAsBytes(loginRequest));
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher pathMatcher = MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/home/recovery/" + cpf));
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, pathMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not update password with invalid password")
        public void testUpdatePasswordWithInvalidPassword() throws Exception {
                final String cause = "Field \"password\".";
                final String message = "The field must have: minimum of 8 characters; minimum of 1 uppercase character; minimum of 1 special character; minimum of 1 digit.";
                final String newPassword = "n3wPassw0rd";
                final String cpf = "26259499027";
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf(cpf);
                signUpRequest.getLogin().setUsername("InvalidPassword");
                if (!userRepository.existsByCpf(cpf)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signUpRequest.getLogin().setPassword(newPassword);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(RECOVERY_ENDPOINT, cpf);
                requestBuilder.content(new JsonMapper().writeValueAsBytes(signUpRequest.getLogin()));
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher pathMatcher = MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/home/recovery/" + cpf));
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.BAD_REQUEST.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, pathMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        @DisplayName("User should not update password with incorrect answer")
        public void testUpdatePasswordWithIncorrectAnswer() throws Exception {
                final String cause = "Field \"recoveryPasswordAnswer\".";
                final String message = "Incorrect answer! You are unauthorized to change password.";
                final String newPassword = "n3wP@ssw0rd";
                final String cpf = "04629936807";
                SignUpRequest signUpRequest = defaultSignUpRequest();
                signUpRequest.setCpf(cpf);
                signUpRequest.getLogin().setUsername("InvalidPassword");
                if (!userRepository.existsByCpf(cpf)) {
                        homeService.signUp(ConverterUtil.from(signUpRequest, UserDto.class));
                }
                signUpRequest.getLogin().setPassword(newPassword);
                signUpRequest.getLogin().setRecoveryPasswordAnswer("Incorrect answer.");
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(RECOVERY_ENDPOINT, cpf);
                requestBuilder.content(new JsonMapper().writeValueAsBytes(signUpRequest.getLogin()));
                requestBuilder.contentType(MediaType.APPLICATION_JSON);
                final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value());
                final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
                final ResultMatcher pathMatcher = MockMvcResultMatchers.jsonPath("$.path", Matchers.is("/v1/home/recovery/" + cpf));
                final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status", Matchers.is(HttpStatus.UNAUTHORIZED.toString()));
                final ResultMatcher causeMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].cause", Matchers.is(cause));
                final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.messages[0].message", Matchers.is(message));
                mockMvc.perform(requestBuilder)
                                .andExpectAll(statusCodeMatcher, timestampMatcher, pathMatcher, statusMatcher, causeMatcher, messageMatcher)
                                .andDo(MockMvcResultHandlers.print());
        }
}

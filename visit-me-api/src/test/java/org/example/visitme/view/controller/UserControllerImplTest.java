package org.example.visitme.view.controller;

import java.util.HashSet;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.HomeService;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.utils.ConverterUtil;
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
    private LoginRepository loginRepository;

    private LoginDto defaultLoginRequest() {
        return new LoginDto("signUpUsername", "Pa$$w0rd", "What is a question?", "This is answer.");
    }

    private Set<PhoneDto> defaultPhoneRequests() {
        Set<PhoneDto> phoneDtos = new HashSet<>();
        phoneDtos.add(new PhoneDto("0143218765"));
        phoneDtos.add(new PhoneDto("01912348765"));
        return phoneDtos;
    }

    private UserDto defaultUserDto() {
        final String fullname = "fullname";
        final String cpf = "04629933883";
        final String email = "a_user.name@domain.com";
        return new UserDto(defaultLoginRequest(), fullname, cpf, email, defaultPhoneRequests());
    }

    @Test
    @DisplayName("User signs out successfully")
    public void testSignOut() throws Exception {
        final String token;
        final String username = "OtherUsername";
        UserDto userDto = defaultUserDto();
        userDto.setCpf("04629937870");
        userDto.getLogin().setUsername(username);
        if (!loginRepository.existsByUsername(username)) {
            homeService.signUp(ConverterUtil.from(userDto, UserDto.class));
        }
        token = "Bearer ".concat(homeService.signIn(userDto.getLogin()));
        final HealthResponse response = new HealthResponse("UP");
        final ResultMatcher statusCodeMatcher = MockMvcResultMatchers.status().isOk();
        final ResultMatcher timestampMatcher = MockMvcResultMatchers.jsonPath("$.timestamp", Matchers.notNullValue());
        final ResultMatcher statusMatcher = MockMvcResultMatchers.jsonPath("$.status",
                Matchers.is(HttpStatus.OK.toString()));
        final ResultMatcher messageMatcher = MockMvcResultMatchers.jsonPath("$.message", Matchers.is("It's up."));
        final ResultMatcher dataMatcher = MockMvcResultMatchers.jsonPath("$.data.status",
                Matchers.is(response.getStatus()));
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
    @DisplayName("User signs out with expired authentication token")
    public void testSignOutWithExpiredAuthenticationToken() throws Exception {
        final String cause = "Header \"Authorization\".";
        final String message = "Token invalid. You must to sign in.";
        final String token = "";
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
}

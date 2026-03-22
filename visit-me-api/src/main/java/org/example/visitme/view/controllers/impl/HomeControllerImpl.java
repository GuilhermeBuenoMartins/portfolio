package org.example.visitme.view.controllers.impl;

import java.time.Instant;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.HomeService;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.view.controllers.HomeController;
import org.example.visitme.view.requests.LoginRequest;
import org.example.visitme.view.requests.SignInRequest;
import org.example.visitme.view.requests.SignUpRequest;
import org.example.visitme.view.responses.HealthResponse;
import org.example.visitme.view.responses.LoginResponse;
import org.example.visitme.view.responses.Response;
import org.example.visitme.view.responses.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/home")
public class HomeControllerImpl implements HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/health")
    public ResponseEntity<Response<HealthResponse>> getHealth() {
        final Response<HealthResponse> response = new Response<>(
                Instant.now(), HttpStatus.OK, "It's up.", new HealthResponse("UP"));
        return new ResponseEntity<Response<HealthResponse>>(response, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Response<SignUpResponse>> signUp(@RequestBody SignUpRequest request) {
        final String message = "You were signed up successfully.";
        final UserDto dto = homeService.signUp(ConverterUtil.from(request, UserDto.class));
        final SignUpResponse response = ConverterUtil.from(dto, SignUpResponse.class);
        return new ResponseEntity<>(new Response<>(Instant.now(), HttpStatus.CREATED, message, response),
                HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Response<String>> signIn(@RequestBody SignInRequest request) {
        final String token = homeService.signIn(ConverterUtil.from(request, LoginDto.class));
        final Response<String> response = new Response<>(Instant.now(), HttpStatus.OK, null, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/recovery/{cpf}")
    public ResponseEntity<Response<String>> getRecoveryPasswordQuestion(@PathVariable String cpf) {
        final String question = homeService.getRecoveryPasswordQuestion(cpf);
        final Response<String> response = new Response<>(Instant.now(), HttpStatus.OK, null, question);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/recovery/{cpf}")
    public ResponseEntity<Response<LoginResponse>> updatePassword(@PathVariable String cpf, @RequestBody LoginRequest request) {
        final String message = "Your password was changed successfully! Please, sign in to system with the new password.";
        UserDto userDto = new UserDto();
        userDto.setCpf(cpf);
        userDto.setLogin(ConverterUtil.from(request, LoginDto.class));
        LoginDto dto = homeService.updatePassword(userDto);
        LoginResponse loginResponse = ConverterUtil.from(dto, LoginResponse.class);
        final Response<LoginResponse> response = new Response<>(Instant.now(), HttpStatus.OK, message, loginResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

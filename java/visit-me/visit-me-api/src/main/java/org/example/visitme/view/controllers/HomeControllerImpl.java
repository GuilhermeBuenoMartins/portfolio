package org.example.visitme.view.controllers;

import java.time.Instant;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.HomeService;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.view.requests.SignInRequest;
import org.example.visitme.view.requests.SignUpRequest;
import org.example.visitme.view.responses.HealthResponse;
import org.example.visitme.view.responses.Response;
import org.example.visitme.view.responses.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        final Response<HealthResponse> RESPONSE = new Response<>(
                Instant.now(), HttpStatus.OK, "It's up.", new HealthResponse("UP"));
        return new ResponseEntity<Response<HealthResponse>>(RESPONSE, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Response<SignUpResponse>> signUp(@RequestBody SignUpRequest request) {
        final String MESSAGE = "You were signed up successfully.";
        final UserDto DTO = homeService.signUp(ConverterUtil.from(request, UserDto.class));
        final SignUpResponse RESPONSE = ConverterUtil.from(DTO, SignUpResponse.class);
        return new ResponseEntity<>(new Response<>(Instant.now(), HttpStatus.CREATED, MESSAGE, RESPONSE),
                HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Response<String>> signIn(@RequestBody SignInRequest request) {
        final String TOKEN = homeService.signIn(ConverterUtil.from(request, LoginDto.class));
        final Response<String> RESPONSE = new Response<>(Instant.now(), HttpStatus.OK, null, TOKEN);
        return new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    }
}

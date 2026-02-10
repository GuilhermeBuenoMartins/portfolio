package org.example.visitme.view.controllers;

import java.time.Instant;

import org.example.visitme.view.responses.HealthResponse;
import org.example.visitme.view.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/v1/users")
public class UserControllerImpl {
    
    @GetMapping("/sign-out")
    public ResponseEntity<Response<HealthResponse>> signOut() {
        final Response<HealthResponse> RESPONSE = new Response<>(
                Instant.now(), HttpStatus.OK, "It's up.", new HealthResponse("UP"));
        return new ResponseEntity<Response<HealthResponse>>(RESPONSE, HttpStatus.OK);
    }
    
}

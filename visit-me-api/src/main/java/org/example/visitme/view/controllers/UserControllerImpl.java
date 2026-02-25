package org.example.visitme.view.controllers;

import java.time.Instant;

import org.example.visitme.control.services.UserService;
import org.example.visitme.view.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/v1/users")
public class UserControllerImpl implements UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/sign-out")
    public ResponseEntity<Response<Boolean>> signOut(@RequestHeader(name = "Authorization") String authorization) {
        final Boolean sucess = userService.signOut(authorization) > -1;
        final Response<Boolean> RESPONSE = new Response<>(
                Instant.now(), HttpStatus.OK, "You are signed out from system.", sucess);
        return new ResponseEntity<Response<Boolean>>(RESPONSE, HttpStatus.OK);
    }
    
}

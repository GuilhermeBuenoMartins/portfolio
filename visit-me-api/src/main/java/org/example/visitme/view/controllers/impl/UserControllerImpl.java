package org.example.visitme.view.controllers.impl;

import java.time.Instant;

import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.UserService;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.view.controllers.UserController;
import org.example.visitme.view.responses.Response;
import org.example.visitme.view.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("")
    public ResponseEntity<Page<UserResponse>> listUser(
        @RequestParam(name = "fullname", required = false) String fullname,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size) {
            Page<UserDto> pageUserDto = userService.searchByFullname(fullname, Pageable.ofSize(size).withPage(page));
            Page<UserResponse> pageUserResponse = ConverterUtil.from(pageUserDto, UserResponse.class);
            return new ResponseEntity<Page<UserResponse>>(pageUserResponse, HttpStatus.OK);
        }
}

package org.example.visitme.view.controllers;

import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.services.UserService;
import org.example.visitme.control.services.ValidationService;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.view.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/v1/users")
public class UserController {
    
    @Autowired
    private UserService service; 

    @Autowired
    private ValidationService validation;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody UserRequest request) {
        validation.signUp(request);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        request.getLogin().setPassword(encoder.encode((String) request.getLogin().getPassword()));
        UserDto dto = (UserDto) ConverterUtil.from(request, UserDto.class);
        dto = service.insert(dto);
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }
    
}

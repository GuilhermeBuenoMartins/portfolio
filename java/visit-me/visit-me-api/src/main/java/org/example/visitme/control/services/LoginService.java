package org.example.visitme.control.services;

import java.util.Optional;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.model.entities.LoginEntity;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private LoginRepository repository;

    public boolean hasUsername(String username) {
        return repository.existsByUsername(username);
    }

    public String authenticate(LoginDto dto) {
        final String LOGIN_MESSAGE_ERROR = "Username or password are incorrect";
        Optional<LoginEntity> optional = repository.findByUsername(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AuthenticationException(new ErrorException("signIn", LOGIN_MESSAGE_ERROR));
        }
        if (!optional.get().getPassword().equals(dto.getPassword()) || !optional.get().getActived()) {
            throw new AuthenticationException(new ErrorException("signIn", LOGIN_MESSAGE_ERROR));
        }
        return jwtUtil.generateToken(dto);
    }
}

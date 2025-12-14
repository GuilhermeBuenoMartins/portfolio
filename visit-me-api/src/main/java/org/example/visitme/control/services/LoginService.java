package org.example.visitme.control.services;

import org.example.visitme.model.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    @Autowired
    private LoginRepository repository;

    public boolean hasUsername(String username) {
        return repository.existsByUsername(username);
    }
}

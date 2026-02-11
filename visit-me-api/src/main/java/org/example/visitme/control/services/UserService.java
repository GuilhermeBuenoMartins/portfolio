package org.example.visitme.control.services;

import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.model.entities.TokenBlacklistEntity;
import org.example.visitme.model.repositories.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    public int signOut(String token) {
        if (!tokenBlacklistRepository.existsByToken(token)) {
            TokenBlacklistEntity entity = new TokenBlacklistEntity(token);
            tokenBlacklistRepository.save(entity);
            return entity.getId();
        }
        final String cause = "Header \"Authorization\".";
        final String message = "Token invalid. You must to sign in.";
        throw new AuthenticationException(new ErrorException(cause, message));
    }
}

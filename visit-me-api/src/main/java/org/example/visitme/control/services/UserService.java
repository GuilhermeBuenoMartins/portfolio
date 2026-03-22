package org.example.visitme.control.services;

import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.model.entities.TokenBlacklistEntity;
import org.example.visitme.model.entities.UserEntity;
import org.example.visitme.model.repositories.TokenBlacklistRepository;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    private UserRepository userRepository;

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

    public Page<UserDto> searchByFullname(String fullName, Pageable pageable) {
        Page<UserEntity> entities = fullName == null ? 
            userRepository.findAll(pageable) : 
            userRepository.findByFullNameContainingIgnoreCase(fullName, pageable);
        return ConverterUtil.from(entities, UserDto.class);
    }
}

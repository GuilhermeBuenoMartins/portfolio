package org.example.visitme.control.services;

import java.util.Optional;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.control.exceptions.ValidationException;
import org.example.visitme.control.services.validations.SignInValidationService;
import org.example.visitme.control.services.validations.SignUpValidationService;
import org.example.visitme.model.entities.LoginEntity;
import org.example.visitme.model.entities.UserEntity;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SignInValidationService signInValidationService;

    @Autowired
    private SignUpValidationService signUpValidationService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    public UserDto signUp(UserDto dto) {
        final Set<ErrorException> ERROR_EXCEPTIONS = signUpValidationService.validate(dto);
        if (ERROR_EXCEPTIONS.size() > 0) {
            throw new ValidationException(ERROR_EXCEPTIONS);
        }
        dto.getLogin().setPassword(encoder.encode(dto.getLogin().getPassword()));
        dto.getLogin()
                .setRecoveryPasswordAnswer(encoder.encode(dto.getLogin().getRecoveryPasswordAnswer().toLowerCase()));
        dto.getLogin().setActived(true);
        UserEntity entity = ConverterUtil.from(dto, UserEntity.class);
        entity.getPhones().stream().forEach(phoneDto -> phoneDto.setUser(entity));
        userRepository.save(entity);
        return ConverterUtil.from(entity, UserDto.class);
    }

    public String signIn(LoginDto dto) {
        final String CAUSE = "Fields \"username\" or \"password\".";
        final String MESSAGE = "Username or password are incorrect.";
        final Set<ErrorException> ERROR_EXCEPTIONS = signInValidationService.validate(dto);
        if (ERROR_EXCEPTIONS.size() > 0) {
            throw new ValidationException(ERROR_EXCEPTIONS);
        }
        Optional<LoginEntity> optional = loginRepository.findByUsername(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AuthenticationException(new ErrorException(CAUSE, MESSAGE));
        }
        if (!encoder.matches(dto.getPassword(), optional.get().getPassword()) || !optional.get().getActived()) {
            throw new AuthenticationException(new ErrorException(CAUSE, MESSAGE));
        }
        return jwtUtil.generateToken(dto);
    }
}

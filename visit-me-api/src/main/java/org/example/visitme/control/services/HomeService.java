package org.example.visitme.control.services;

import java.util.Optional;
import java.util.Set;

import org.example.visitme.control.dto.LoginDto;
import org.example.visitme.control.dto.UserDto;
import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.example.visitme.control.exceptions.NotFoundException;
import org.example.visitme.control.exceptions.ValidationException;
import org.example.visitme.control.services.validations.SignInValidationService;
import org.example.visitme.control.services.validations.SignUpValidationService;
import org.example.visitme.model.entities.LoginEntity;
import org.example.visitme.model.entities.UserEntity;
import org.example.visitme.model.repositories.LoginRepository;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.example.visitme.utils.JwtUtil;
import org.example.visitme.utils.ValidatorUtil;
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
        final Set<ErrorException> errorExceptions = signUpValidationService.validate(dto);
        if (errorExceptions.size() > 0) {
            throw new ValidationException(errorExceptions);
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
        final String cause = "Fields \"username\" or \"password\".";
        final String message = "Username or password are incorrect.";
        final Set<ErrorException> errorExceptions = signInValidationService.validate(dto);
        if (errorExceptions.size() > 0) {
            throw new ValidationException(errorExceptions);
        }
        Optional<LoginEntity> optional = loginRepository.findByUsername(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AuthenticationException(new ErrorException(cause, message));
        }
        if (!encoder.matches(dto.getPassword(), optional.get().getPassword()) || !optional.get().getActived()) {
            throw new AuthenticationException(new ErrorException(cause, message));
        }
        return jwtUtil.generateToken(dto);
    }

    public String getRecoveryPasswordQuestion(String cpf) {
        if (!ValidatorUtil.validateCPF(cpf)) {
            final String cause = "Path \"CPF\".";
            final String message = "The field must have: exactly 11 digits; no special characters.";
            ErrorException error = new ErrorException(cause, message);
            throw new ValidationException(Set.of(error));
        }
        Optional<UserEntity> optional = userRepository.findByCpf(cpf);
        if (optional.isEmpty()) {
            final String cause = "Path \"CPF\".";
            final String message = "This CPF does not exist in the system. Please, sign up.";
            throw new NotFoundException(cause, message);
        }
        return optional.get().getLogin().getRecoveryPasswordQuestion();
    }
}

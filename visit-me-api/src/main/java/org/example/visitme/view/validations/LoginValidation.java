package org.example.visitme.view.validations;

import org.example.visitme.utils.ValidatorUtil;
import org.springframework.stereotype.Component;

@Component
public class LoginValidation {

    public boolean validateUsername(Object value) {
        final String username = (String) value;
        if (!ValidatorUtil.validateLength(username, 8, 64)) { return false; }
        if (username.contains(" ")) {return false; }
        return true;
    }

    public boolean validatePassword(Object value) {
        final String password = (String) value;
        if (password == null) { return false; }
        if (password.matches(".*\\d+.*") && password.matches(".*[\\W+|_+].*") && password.matches(".*[A-Z].*") & password.matches(".*[a-z].*")) { 
            return true; 
        }
        return false;
    }

    public boolean validateRecoveryPasswordQuestion(Object value) {
        final String recoveryPasswordQuestion = (String) value;
        return ValidatorUtil.validateLength(recoveryPasswordQuestion, 8, 256);
    }

    public boolean validateRecoveryPasswordAnswer(Object value) {
        final String recoveryPasswordAnswer = (String) value;
        return ValidatorUtil.validateLength(recoveryPasswordAnswer, 8, 256);
    }

}
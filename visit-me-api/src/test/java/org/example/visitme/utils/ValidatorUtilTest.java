package org.example.visitme.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test of ValidatorUtil")
public class ValidatorUtilTest {

    @Test
    @DisplayName("CPF should be valid")
    void testValidCPF() {
        final String CPF = "09853843013";
        Assertions.assertTrue(ValidatorUtil.validateCPF(CPF));
    }

    @Test
    @DisplayName("CPF should not be invalid")
    void testPasswordWithInvalidDigit() {
        final String CPF = "09853843003";
        Assertions.assertFalse(ValidatorUtil.validateCPF(CPF));
    }

    @Test
    @DisplayName("CPF should not be null")
    void testCpfNull() {
        final String CPF = null;
        Assertions.assertFalse(ValidatorUtil.validateCPF(CPF));
    }

    @Test
    @DisplayName("CPF length should be greater than 10")
    void testCpfWith10characters() {
        final String CPF = "9853843013";
        Assertions.assertFalse(ValidatorUtil.validateCPF(CPF));
    }

    @Test
    @DisplayName("CPF length should be lesser than 12")
    void testCpfWith12characters() {
        final String CPF = "009853843013";
        Assertions.assertFalse(ValidatorUtil.validateCPF(CPF));
    }

    @Test
    @DisplayName("CPF should contain just digits")
    void testCpfWithLetters() {
        final String CPF = "-9853843013";
        Assertions.assertFalse(ValidatorUtil.validateCPF(CPF));
    }

    @Test
    @DisplayName("Email should be valid")
    void testValidEmail() {
        final String EMAIL = "a.user_name@domain.com";
        Assertions.assertTrue(ValidatorUtil.validateEmail(EMAIL));
    }

    @Test
    @DisplayName("Email should not be null")
    void testEmailNull() {
        final String EMAIL = null;
        Assertions.assertFalse(ValidatorUtil.validateEmail(EMAIL));
    }

    @Test
    @DisplayName("Email should have a valid format")
    void testEmailFormat() {
        final String EMAIL = "a.user_namedomain.com";
        Assertions.assertFalse(ValidatorUtil.validateEmail(EMAIL));
    }

    // @Test
    // @DisplayName("Password should be valid")
    // void testValidPassword() {
    //     final String PASSAWORD = "pa*W0rd";
    //     Assertions.assertTrue(ValidatorUtil.validatePassword(PASSAWORD));
    // }

    // @Test
    // @DisplayName("Password should contain a digit")
    // void testPasswordWithoutDigit() {
    //     final String PASSAWORD = "pa*Word";
    //     Assertions.assertFalse(ValidatorUtil.validatePassword(PASSAWORD));
    // }

    // @Test
    // @DisplayName("Password should contain a special character")
    // void testPasswordWithoutSpecialCharacter() {
    //     final String PASSAWORD = "passW0rd";
    //     Assertions.assertFalse(ValidatorUtil.validatePassword(PASSAWORD));
    // }

    // @Test
    // @DisplayName("Password should contain a uppercase letter")
    // void testPasswordWithoutUppercaseLetter() {
    //     final String PASSAWORD = "pa*w0rd";
    //     Assertions.assertFalse(ValidatorUtil.validatePassword(PASSAWORD));
    // }

    // @Test
    // @DisplayName("Password should contain a lowercase letter")
    // void testPasswordWithoutLowercaseLetter() {
    //     final String PASSAWORD = "PA*W0RD";
    //     Assertions.assertFalse(ValidatorUtil.validatePassword(PASSAWORD));
    // }

}
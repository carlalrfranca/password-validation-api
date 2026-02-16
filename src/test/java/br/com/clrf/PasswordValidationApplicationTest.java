package br.com.clrf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PasswordValidationApplicationTest {
    @Test
    void shouldStartApplicationWithoutException() {
        assertDoesNotThrow(() -> PasswordValidationApplication.main(new String[]{}));
    }
}

package br.com.clrf.service;

import br.com.clrf.domain.policy.CompositePasswordPolicy;
import br.com.clrf.domain.rules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUseCaseTest {

    private static final String SPECIAL = "!@#$%^&*()-+";
    private static final int MIN_LENGTH = 9;

    private PasswordUseCase useCase;

    @BeforeEach
    void setUp() {
        var policy = new CompositePasswordPolicy(
                List.of(
                        new NoWhiteSpaceRule(),
                        new OnlyAllowedCharRule(SPECIAL),
                        new MinLengthRule(MIN_LENGTH),
                        new HasDigitRule(),
                        new HasLowercaseRule(),
                        new HasUppercaseRule(),
                        new HasSpecialCharRule(SPECIAL),
                        new NoRepeatedCharRule()
                )
        );

        useCase = new PasswordUseCase(policy);
    }

    @Test
    void shouldReturnTrueWhenPasswordIsValid() {
        assertTrue(useCase.execute("AbTp9!fok"));
    }

    @Test
    void shouldReturnFalseWhenPasswordIsInvalid() {
        assertFalse(useCase.execute("AbTp9fok"));
    }

    @Test
    void shouldReturnFalseWhenNull() {
        assertFalse(useCase.execute(null));
    }
}
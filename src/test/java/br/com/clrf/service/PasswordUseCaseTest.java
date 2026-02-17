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
    private List<String> valid;
    private List<String> invalid;

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
        valid = List.of("AbTp9!fok", "AbTp9!fokC");
        invalid = List.of("", "aa", "AAAbbbCc", "AbTp9!foo", "AbTp9!foA", "AbTp9 fok", "AbTp9!fok\t", "AbTp9!fok_");
    }

    @Test
    void shouldReturnTrueForValidPasswords() {
        for (String p : valid) {
            assertTrue(useCase.execute(p));
        }
    }

    @Test
    void shouldReturnTrueFOrInvalidPasswords() {
        for (String p : invalid) {
            assertFalse(useCase.execute(p));
        }
    }
}
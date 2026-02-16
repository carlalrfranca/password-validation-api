package br.com.clrf.domain.policy;

import br.com.clrf.domain.rules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompositePasswordPolicyTest {

    private static final String SPECIAL = "!@#$%^&*()-+";
    private static final int MIN_LENGTH = 9;

    private CompositePasswordPolicy policy;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        policy = new CompositePasswordPolicy(
                List.of(
                        new NoWhiteSpaceRule(),
                        new MinLengthRule(MIN_LENGTH),
                        new HasDigitRule(),
                        new HasLowercaseRule(),
                        new HasUppercaseRule(),
                        new HasSpecialCharRule(SPECIAL),
                        new NoRepeatedCharRule()
                )
        );

        valid = List.of(
                "AbTp9!fok",
                "AbTp9!fok_"
        );

        invalid = List.of(
                "",
                "aa",
                "AAAbbbCc",
                "AbTp9!foo",
                "AbTp9!foA",
                "AbTp9 fok",
                "AbTp9!fok\t"
        );
    }

    @Test
    void shouldReturnFailureWhenPasswordIsNull() {
        assertTrue(policy.passwordValidate(null).isPresent());
    }

    @Test
    void shouldReturnTrueForValidPasswords() {
        for (String p : valid) {
            assertTrue(
                    policy.passwordValidate(p).isEmpty(),
                    "Expected true for: [" + p + "]"
            );
        }
    }

    @Test
    void shouldReturnFalseForInvalidPasswords() {
        for (String p : invalid) {
            assertTrue(
                    policy.passwordValidate(p).isPresent(),
                    "Expected false for: [" + p + "]"
            );
        }
    }
}

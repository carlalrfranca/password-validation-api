package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinLengthRuleTest {

    private MinLengthRule rule;
    private List<String> invalid;
    private List<String> valid;
    private static final int MIN_LENGTH = 9;

    @BeforeEach
    void setUp() {
        rule = new MinLengthRule(MIN_LENGTH);
        invalid = List.of("", "Ab1!", "a", "AbTp9!fo", "AbTp9 fo");
        valid = List.of("AbTp9!fok", "@QabC123!", "Senha@123", "AbTp9!fokA", "AbTp9!fok1");
    }
    @Test
    void shouldReturnFalseForInvalidPasswords() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnTrueForValidPasswords() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }
}

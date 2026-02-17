package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HasSpecialCharRuleTest {

    private static final String SPECIAL = "!@#$%^&*()-+";
    private HasSpecialCharRule rule;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        rule = new HasSpecialCharRule(SPECIAL);
        valid = List.of("AbTp9!fok", "a1+", "A-9b");
        invalid = List.of("AbTp9fok", "ABC123def", "a1B2c3", "abc?123");
    }

    @Test
    void shouldReturnTrueWhenHasAllowedSpecialChar() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnFalseWhenNoSpecialChar() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }
}
package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HasSpecialCharRuleTest {

    private static final String SPECIAL = "!@#$%^&*()-+";
    private HasSpecialCharRule rule;
    private List<String> invalid;
    private List<String> valid;

    @BeforeEach
    void setUp() {
        rule = new HasSpecialCharRule(SPECIAL);

        invalid = List.of("AbTp9fok", "ABC123def", "a1B2c3");
        valid = List.of("AbTp9!fok", "a1+", "A-9b");
    }

    @Test
    void shouldReturnFalseWhenNoSpecialChar() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnTrueWhenHasAllowedSpecialChar() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnFalseWhenHasAllowedSpecialChar() {
        assertFalse(rule.isSatisfiedBy("abc?123"));
    }
}
package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HasUppercaseRuleTest {

    private HasUppercaseRule rule;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        rule = new HasUppercaseRule();
        valid = List.of("a1B", "AbTp9!fok", "ABC1!", "A", "ABC");
        invalid = List.of("abcdef", "123456", "!@#$%^&*()");
    }

    @Test
    void shouldReturnTrueWhenHasUppercase() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnFalseWhenNoUppercase() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }
}

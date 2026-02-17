package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HasDigitRuleTest {

    private HasDigitRule rule;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        rule = new HasDigitRule();
        invalid = List.of("abcdef", "ABCDEF");
        valid = List.of("a1", "AbTp9!fok", "ABC1!", "1", "123");
    }

    @Test
    void shouldReturnTrueWhenHasDigit() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnFalseWhenNoDigit() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }
}

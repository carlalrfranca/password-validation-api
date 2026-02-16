package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HasDigitRuleTest {

    private HasDigitRule rule;
    private List<String> invalid;
    private List<String> valid;

    @BeforeEach
    void setUp() {
        rule = new HasDigitRule();

        invalid = List.of(
                "abcdef",
                "ABCDEF"
        );

        valid = List.of(
                "a1",
                "AbTp9!fok",
                "ABC1!"
        );
    }

    @Test
    void shouldReturnFalseWhenNoDigit() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnTrueWhenHasDigit() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnTrueWhenOnlyDigits() {
        assertTrue(rule.isSatisfiedBy("1"));
        assertTrue(rule.isSatisfiedBy("123"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        assertThrows(NullPointerException.class,
                () -> rule.isSatisfiedBy(null));
    }
}

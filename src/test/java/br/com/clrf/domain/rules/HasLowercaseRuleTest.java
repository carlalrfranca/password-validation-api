package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HasLowercaseRuleTest {

    private HasLowercaseRule rule;
    private List<String> invalid;
    private List<String> valid;

    @BeforeEach
    void setUp() {
        rule = new HasLowercaseRule();

        invalid = List.of( "ABCDEF", "ABC123HUH!", "ABTP9!FOK");

        valid = List.of("abcdefg", "AbTp9!fok");
    }

    @Test
    void shouldReturnFalseWhenNoLowercase() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }
    @Test
    void shouldReturnTrueWhenHasLowercase() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnTrueWhenOnlyLowercase() {
        assertTrue(rule.isSatisfiedBy("a"));
        assertTrue(rule.isSatisfiedBy("abc"));
    }
}

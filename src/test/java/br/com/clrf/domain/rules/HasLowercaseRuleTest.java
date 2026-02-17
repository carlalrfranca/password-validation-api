package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HasLowercaseRuleTest {

    private HasLowercaseRule rule;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        rule = new HasLowercaseRule();
        valid = List.of("abcdefg", "AbTp9!fok", "a", "abc");
        invalid = List.of( "ABCDEF", "ABC123HUH!", "ABTP9!FOK");
    }

    @Test
    void shouldReturnTrueWhenHasLowercase() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnFalseWhenNoLowercase() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }
}

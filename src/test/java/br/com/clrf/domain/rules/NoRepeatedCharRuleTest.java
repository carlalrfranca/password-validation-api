package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoRepeatedCharRuleTest {

    private NoRepeatedCharRule rule;
    private List<String> invalid;
    private List<String> valid;

    @BeforeEach
    void setUp() {
        rule = new NoRepeatedCharRule();
        invalid = List.of("aa", "AbTp9!foo!", "AAbb12!@");
        valid = List.of("ab", "AbTp9!fok", "A1b2C3!@");
    }

    @Test
    void shouldReturnFalseWhenHasRepeatedChars() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnTrueWhenNoRepeatedChars() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }
}

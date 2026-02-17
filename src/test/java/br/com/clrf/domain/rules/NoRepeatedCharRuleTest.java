package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoRepeatedCharRuleTest {

    private NoRepeatedCharRule rule;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        rule = new NoRepeatedCharRule();
        valid = List.of("ab", "AbTp9!fok", "A1b2C3!@");
        invalid = List.of("aa", "AbTp9!foo!", "AAbb12!@");
    }

    @Test
    void shouldReturnTrueWhenNoRepeatedChars() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnFalseWhenHasRepeatedChars() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }
}

package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NoWhiteSpaceRuleTest {

    private NoWhiteSpaceRule rule;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        rule = new NoWhiteSpaceRule();
        valid = List.of("AbTp9!fok", "ABCdef123!", "!@#$%^&*()-+", "a_b-c.d");
        invalid = List.of("a b", "ab ", " a", "a\tb", "a\nb", "a\rb");
    }

    @Test
    void shouldReturnFalseWhenPasswordIsNull() {
        assertFalse(rule.isSatisfiedBy(null));
    }

    @Test
    void shouldReturnTrueWhenPasswordHasNoWhitespace() {
        for (String password : valid) {
            assertTrue(rule.isSatisfiedBy(password));
        }
    }

    @Test
    void shouldReturnFalseWhenPasswordContainsWhitespace() {
        for (String password : invalid) {
            assertFalse(rule.isSatisfiedBy(password));
        }
    }
}
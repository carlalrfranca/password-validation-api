package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoWhiteSpaceRuleTest {

    private NoWhiteSpaceRule rule;
    private List<String> passwordsWithWhitespace;
    private List<String> passwordsWithoutWhitespace;

    @BeforeEach
    void setUp() {
        rule = new NoWhiteSpaceRule();

        passwordsWithWhitespace = List.of("a b", "ab ", " a", "a\tb", "a\nb", "a\rb");
        passwordsWithoutWhitespace = List.of("AbTp9!fok", "ABCdef123!", "!@#$%^&*()-+", "a_b-c.d");
    }

    @Test
    void shouldReturnFalseWhenPasswordIsNull() {
        assertFalse(rule.isSatisfiedBy(null));
    }

    @Test
    void shouldReturnFalseWhenPasswordContainsWhitespace() {
        for (String password : passwordsWithWhitespace) {
            assertFalse(rule.isSatisfiedBy(password),
                    "Expected false for: [" + password + "]");
        }
    }

    @Test
    void shouldReturnTrueWhenPasswordHasNoWhitespace() {
        for (String password : passwordsWithoutWhitespace) {
            assertTrue(rule.isSatisfiedBy(password),
                    "Expected true for: [" + password + "]");
        }
    }
}
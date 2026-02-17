package br.com.clrf.domain.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OnlyAllowedCharRuleTest {

    private static final String SPECIAL = "!@#$%^&*()-+";
    private OnlyAllowedCharRule rule;
    private List<String> valid;
    private List<String> invalid;

    @BeforeEach
    void setUp() {
        rule = new OnlyAllowedCharRule(SPECIAL);
        valid = List.of("AbTp9!fok", "ABCdef123-", "aB1+");
        invalid = List.of("Ab Tp9!fok", "AbTp9!fok_", "AbTp9!fok=");
    }

    @Test
    void shouldReturnTrueWhenAllCharsAreAllowed() {
        for (String p : valid) {
            assertTrue(rule.isSatisfiedBy(p));
        }
    }

    @Test
    void shouldReturnFalseWhenContainsNotAllowedChars() {
        for (String p : invalid) {
            assertFalse(rule.isSatisfiedBy(p));
        }
    }
}
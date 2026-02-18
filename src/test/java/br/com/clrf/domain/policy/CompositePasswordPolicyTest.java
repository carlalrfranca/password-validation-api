package br.com.clrf.domain.policy;

import br.com.clrf.domain.rules.PasswordRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositePasswordPolicyTest {

    @Mock
    private PasswordRule rule1;

    @Mock
    private PasswordRule rule2;

    private CompositePasswordPolicy policy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        policy = new CompositePasswordPolicy(List.of(rule1, rule2));
    }

    @Test
    void shouldReturnEmptyWhenAllRulesSatisfied() {
        when(rule1.isSatisfiedBy("abc")).thenReturn(true);
        when(rule2.isSatisfiedBy("abc")).thenReturn(true);
        Optional<String> result = policy.passwordValidate("abc");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnFirstFailedRuleName() {
        when(rule1.isSatisfiedBy("abc")).thenReturn(true);
        when(rule2.isSatisfiedBy("abc")).thenReturn(false);
        Optional<String> result = policy.passwordValidate("abc");
        assertTrue(result.isPresent());
        assertEquals(rule2.getClass().getSimpleName(), result.get());
    }
}

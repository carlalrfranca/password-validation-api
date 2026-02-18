package br.com.clrf.service;

import br.com.clrf.domain.policy.PasswordPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordUseCaseTest {

    @Mock
    private PasswordPolicy policy;
    private PasswordUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new PasswordUseCase(policy);
    }

    @Test
    void shouldReturnTrueWhenNoRulesFails() {
        when(policy.passwordValidate("valid")).thenReturn(Optional.empty());
        boolean result = useCase.execute("valid");
        assertTrue(result);
        verify(policy).passwordValidate("valid");
    }

    @Test
    void shouldReturnFalseWhenNoRulesFails() {
        when(policy.passwordValidate("invalid")).thenReturn(Optional.of("Failed rule"));
        boolean result = useCase.execute("invalid");
        assertFalse(result);
        verify(policy).passwordValidate("invalid");
    }

    @Test
    void shouldReturnTrueWhenOptionalIsEmpty() {
        when(policy.passwordValidate("valid")).thenReturn(Optional.empty());
        assertTrue(useCase.execute("valid"));
    }

    @Test
    void shouldReturnFalseAndLogWhenOptionalIsPresent() {
        when(policy.passwordValidate("invalid")).thenReturn(Optional.of("Failed rule"));
        assertFalse(useCase.execute("invalid"));
    }
}
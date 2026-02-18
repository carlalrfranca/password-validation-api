package br.com.clrf.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordValidationConfigTest {

    @Mock
    private PasswordValidationConfig config;

    @BeforeEach
    void setUp() {
        config = new PasswordValidationConfig();
    }

    @Test
    void shouldCreatePasswordPolicyBean() {
        var policy = config.passwordPolicy();
        assert policy != null;
    }

    @Test
    void shouldCreatePasswordUseCaseBean() {
        var policy = config.passwordPolicy();
        var useCase = config.passwordUseCase(policy);
        assert useCase != null;
    }
}
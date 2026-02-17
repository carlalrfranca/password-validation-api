package br.com.clrf.config;

import br.com.clrf.domain.policy.PasswordPolicy;
import br.com.clrf.service.PasswordUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

class PasswordValidationConfigTest {

    @Test
    void shouldLoadPasswordPolicyBean() {
        try (var context = new AnnotationConfigApplicationContext(PasswordValidationConfig.class)) {

            PasswordPolicy policy = context.getBean(PasswordPolicy.class);

            assertNotNull(policy);
        }
    }

    @Test
    void shouldLoadPasswordUseCaseBean() {
        try (var context = new AnnotationConfigApplicationContext(PasswordValidationConfig.class)) {

            PasswordUseCase useCase = context.getBean(PasswordUseCase.class);

            assertNotNull(useCase);
        }
    }
}
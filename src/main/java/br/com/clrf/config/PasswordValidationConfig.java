package br.com.clrf.config;

import br.com.clrf.domain.policy.CompositePasswordPolicy;
import br.com.clrf.domain.policy.PasswordPolicy;
import br.com.clrf.domain.rules.*;
import br.com.clrf.service.PasswordUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class PasswordValidationConfig {

    private static final int MIN_LENGTH = 9;
    private static final String SPECIAL = "!@#$%^&*()-+";

    @Bean
    public PasswordPolicy passwordPolicy() {
        return new CompositePasswordPolicy(List.of(
                new NoWhiteSpaceRule(),
                new MinLengthRule(MIN_LENGTH),
                new HasDigitRule(),
                new HasLowercaseRule(),
                new HasUppercaseRule(),
                new HasSpecialCharRule(SPECIAL),
                new NoRepeatedCharRule()
        ));
    }

    @Bean
    public PasswordUseCase passwordUseCase(PasswordPolicy passwordPolicy) {
        return new PasswordUseCase(passwordPolicy);
    }
}


package br.com.clrf.service;

import br.com.clrf.domain.policy.PasswordPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
//#1 : A PasswordUseCase funciona como porta de entrada
public class PasswordUseCase {

    private final PasswordPolicy rules;

    public boolean execute(String password) {
        Optional<String> failedRule = rules.passwordValidate(password);

        if (failedRule.isPresent()) {
            log.warn("Password validation failed | rule={}", failedRule.get());
            return false;
        }
        return true;
    }
}
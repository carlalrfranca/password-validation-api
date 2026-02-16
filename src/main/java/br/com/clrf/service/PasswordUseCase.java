package br.com.clrf.service;

import br.com.clrf.domain.policy.PasswordPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PasswordUseCase {

    private final PasswordPolicy rules;

    public boolean execute(String password) {
        Optional<String> failedRule = rules.passwordValidate(password);

        if (failedRule.isPresent()) {
            log.error("\u001B[34mPassword validation failed | rule = {}\u001B[0m",
                    failedRule.get());
            return false;
        }
        return true;
    }
}
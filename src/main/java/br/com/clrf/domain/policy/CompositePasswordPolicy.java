package br.com.clrf.domain.policy;

import br.com.clrf.domain.rules.PasswordRule;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CompositePasswordPolicy implements PasswordPolicy {

    private final List<PasswordRule> rules;

    @Override
    public Optional<String> passwordValidate(String password) {

        if (password == null) {
            return Optional.of("Password cannot be null");
        }
        return rules.stream().filter(rule -> !rule.isSatisfiedBy(password))
                .map(rule -> rule.getClass().getSimpleName()).findFirst();
    }
}

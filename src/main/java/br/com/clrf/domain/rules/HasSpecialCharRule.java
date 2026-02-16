package br.com.clrf.domain.rules;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HasSpecialCharRule implements PasswordRule {

        private final String specialChars;

        @Override
        public boolean isSatisfiedBy(String password) {

        return password.chars().anyMatch(c -> specialChars.indexOf(c) >= 0);

    }
}

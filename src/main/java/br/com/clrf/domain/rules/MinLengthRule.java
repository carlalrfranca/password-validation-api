package br.com.clrf.domain.rules;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MinLengthRule implements PasswordRule {

    private final int minLength;

    @Override
    public boolean isSatisfiedBy(String password) {

        return password.length() >= minLength;
    }
}

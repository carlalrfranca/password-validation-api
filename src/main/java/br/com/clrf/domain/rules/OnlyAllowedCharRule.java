package br.com.clrf.domain.rules;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnlyAllowedCharRule implements PasswordRule {

    private final String specialChars;

    @Override
    public boolean isSatisfiedBy(String password) {
        for (char c : password.toCharArray()) {
            if (!isAllowed(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllowed(char c) {
        return Character.isLetterOrDigit(c) || specialChars.indexOf(c) >= 0;
    }
}

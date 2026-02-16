package br.com.clrf.domain.rules;

public class HasDigitRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {

        return password.chars().anyMatch(Character::isDigit);
    }
}

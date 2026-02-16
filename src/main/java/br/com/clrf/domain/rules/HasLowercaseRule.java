package br.com.clrf.domain.rules;

public class HasLowercaseRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {

        return password.chars().anyMatch(Character::isLowerCase);
    }
}

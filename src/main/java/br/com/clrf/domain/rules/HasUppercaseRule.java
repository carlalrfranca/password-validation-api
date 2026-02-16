package br.com.clrf.domain.rules;

public class HasUppercaseRule implements PasswordRule{

    @Override
    public boolean isSatisfiedBy(String password) {

        return password.chars().anyMatch(Character::isUpperCase);
    }
}

package br.com.clrf.domain.rules;

public class NoRepeatedCharRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        return password != null && password.chars().distinct().count() == password.length();
    }
}

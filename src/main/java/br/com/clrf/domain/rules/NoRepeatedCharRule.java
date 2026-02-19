package br.com.clrf.domain.rules;

import java.util.HashSet;
import java.util.Set;

public class NoRepeatedCharRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        return password != null && password.chars().distinct().count() == password.length();
    }
}

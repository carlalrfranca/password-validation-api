package br.com.clrf.domain.rules;

import java.util.HashSet;
import java.util.Set;

public class NoRepeatedCharRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        Set<Character> seen = new HashSet<>();
        for (char c : password.toCharArray()) {
            if (!seen.add(c)) {
                return false;
            }
        }
        return true;
    }
}

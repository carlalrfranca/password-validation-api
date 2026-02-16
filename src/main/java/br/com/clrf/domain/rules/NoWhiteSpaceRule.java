package br.com.clrf.domain.rules;

public class NoWhiteSpaceRule implements PasswordRule {

    @Override
    public boolean isSatisfiedBy(String password) {
        if (password == null) {
            return false;
        }
        return password.chars().noneMatch(Character::isWhitespace);
    }
}

package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class RegexAcceptor implements CharAcceptor{
    private final String regex;

    public RegexAcceptor(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean accepts(char c) {
        return ("" + c).matches(regex);
    }
}

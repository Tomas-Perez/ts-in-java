package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class SingleCharAcceptor implements CharAcceptor{
    private final char match;

    public SingleCharAcceptor(char match) {
        this.match = match;
    }

    @Override
    public boolean accepts(char c) {
        return c == match;
    }
}

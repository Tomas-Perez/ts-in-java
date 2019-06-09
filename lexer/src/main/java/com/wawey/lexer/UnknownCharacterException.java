package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class UnknownCharacterException extends RuntimeException {
    public UnknownCharacterException(char c, int line, int column) {
        super(String.format("Unknown character \"%s\" at line %d, column %d", c, line, column));
    }
}

package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public interface BasicToken {
    TokenType getType();

    String getLexeme();
}

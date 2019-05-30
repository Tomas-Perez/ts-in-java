package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class NoMatchException extends RuntimeException {
    private final TokenType tokenType;
    private final String lexeme;

    public NoMatchException(TokenType tokenType, String lexeme) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}

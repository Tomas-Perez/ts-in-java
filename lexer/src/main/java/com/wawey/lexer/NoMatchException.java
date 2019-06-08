package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class NoMatchException extends RuntimeException {
    private final TokenType tokenType;
    private final String lexeme;

    public NoMatchException(TokenType tokenType, String lexeme) {
        super(String.format("%s does not match \"%s\"", tokenType, lexeme));
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

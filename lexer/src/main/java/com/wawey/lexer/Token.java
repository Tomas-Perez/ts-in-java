package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class Token {
    private TokenType type;
    private String lexeme;
    private int column;
    private int line;

    public Token(TokenType type, String lexeme, int column, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.column = column;
        this.line = line;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }
}


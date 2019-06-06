package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public enum TokenType {
    STRING_LITERAL,
    NUMBER_LITERAL,
    IDENTIFIER,
    SPACE,
    NEWLINE,
    EOF,
    STRING_TYPE("string"),
    NUMBER_TYPE("number"),
    PLUS("+"),
    MINUS("-"),
    ASTERISK("*"),
    FORWARD_SLASH("/"),
    LEFT_PAREN("("),
    RIGHT_PAREN(")"),
    EQUALS("="),
    COLON(":"),
    SEMICOLON(";"),
    LET("let"),
    PRINT("print");

    private String lexeme;
    private boolean fixed;

    TokenType() {
        this.lexeme = null;
        this.fixed = false;
    }

    TokenType(String lexeme) {
        this.lexeme = lexeme;
        this.fixed = true;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", name(), lexeme);
    }

    public String getLexeme() {
        return lexeme;
    }

    public boolean isFixed() {
        return fixed;
    }
}

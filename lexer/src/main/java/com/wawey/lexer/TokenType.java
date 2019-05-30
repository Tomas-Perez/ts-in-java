package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
enum TokenType {
    STRING_LITERAL("string literal"),
    NUMBER_LITERAL("numeric literal"),
    STRING_TYPE("string"),
    NUMBER_TYPE("number"),
    IDENTIFIER("identifier"),
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

    private String strRep;

    TokenType(String strRep) {
        this.strRep = strRep;
    }

    @Override
    public String toString() {
        return strRep;
    }
}

package com.wawey.lexer;

public interface Token {
    TokenType getType();
    String getLexeme();
    int getStartColumn();
    int getEndColumn();
    int getLine();
}

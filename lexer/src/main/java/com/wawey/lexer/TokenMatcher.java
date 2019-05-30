package com.wawey.lexer;

public interface TokenMatcher {
    int matchLength();
    boolean isMatching();
    boolean match(char c);
    BasicToken getBasicToken();
    void reset();
}

package com.wawey.lexer;

public interface TokenMatcher {
    boolean isMatching();
    boolean match(char c);
    BasicToken getBasicToken();
    void reset();
}

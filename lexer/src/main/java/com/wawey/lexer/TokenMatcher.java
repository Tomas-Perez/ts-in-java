package com.wawey.lexer;

public interface TokenMatcher {
    boolean isMatching();
    boolean match(char c);
    boolean acceptable();
    BasicToken getBasicToken();
    void reset();
}

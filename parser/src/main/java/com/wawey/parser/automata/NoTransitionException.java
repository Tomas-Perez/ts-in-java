package com.wawey.parser.automata;

import com.wawey.lexer.Token;

public class NoTransitionException extends RuntimeException {
    private Token token;

    public NoTransitionException(Token token) {
        super("No transition for token " + token);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}

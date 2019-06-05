package com.wawey.parser.automata;

import com.wawey.lexer.Token;

public class NoTransitionException extends RuntimeException {
    public NoTransitionException(Token token) {
        super("No transition for token " + token);
    }
}

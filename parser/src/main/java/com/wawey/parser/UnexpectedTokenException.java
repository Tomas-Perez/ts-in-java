package com.wawey.parser;

import com.wawey.lexer.Token;

public class UnexpectedTokenException extends RuntimeException {
    public UnexpectedTokenException(Token token) {
        super(
                String.format(
                        "Unexpected %s Token \"%s\" at line %d column %d",
                        token.getType().name(),
                        token.getLexeme(),
                        token.getLine(),
                        token.getStartColumn()
                )
        );
    }
}

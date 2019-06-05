package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.Rule;

public class UnexpectedTokenException extends RuntimeException {
    public UnexpectedTokenException(Rule rule, Token token) {
        super(
                String.format(
                        "Trying to parse rule: %s found unexpected Token \"%s\" at line %d column %d",
                        rule,
                        token.getLexeme(),
                        token.getLine(),
                        token.getStartColumn()
                )
        );
    }
}

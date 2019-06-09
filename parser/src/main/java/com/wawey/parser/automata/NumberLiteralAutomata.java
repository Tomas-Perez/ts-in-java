package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.NumberLiteralNode;

public class NumberLiteralAutomata extends TerminalNodeAutomata {

    public NumberLiteralAutomata() {
        super(
                Rule.NUMBER_LITERAL,
                TokenType.NUMBER_LITERAL,
                token -> new NumberLiteralNode(token.getLine(), token.getStartColumn(), token.getLexeme())
        );
    }
}

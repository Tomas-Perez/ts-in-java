package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.NumberLiteralNode;

public class NumberLiteralAutomata extends TerminalNodeParser {

    public NumberLiteralAutomata() {
        super(
                TokenType.NUMBER_LITERAL,
                token -> new NumberLiteralNode(token.getLine(), token.getStartColumn(), token.getLexeme())
        );
    }
}

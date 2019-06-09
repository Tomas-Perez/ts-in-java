package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.IdentifierNode;

public class IdentifierAutomata extends TerminalNodeAutomata {

    public IdentifierAutomata() {
        super(
                Rule.IDENTIFIER,
                TokenType.IDENTIFIER,
                token -> new IdentifierNode(token.getLine(), token.getStartColumn(), token.getLexeme())
        );
    }
}

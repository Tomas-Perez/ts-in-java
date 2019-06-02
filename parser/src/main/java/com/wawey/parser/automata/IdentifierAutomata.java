package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.IdentifierNode;

public class IdentifierAutomata extends TerminalNodeParser {

    public IdentifierAutomata() {
        super(
                TokenType.IDENTIFIER,
                token -> new IdentifierNode(token.getLine(), token.getStartColumn(), token.getLexeme())
        );
    }
}

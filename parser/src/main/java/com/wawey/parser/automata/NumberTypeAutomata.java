package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.NumberTypeNode;

public class NumberTypeAutomata extends TerminalNodeParser {

    public NumberTypeAutomata() {
        super(TokenType.NUMBER_TYPE, token -> new NumberTypeNode(token.getLine(), token.getStartColumn()));
    }
}

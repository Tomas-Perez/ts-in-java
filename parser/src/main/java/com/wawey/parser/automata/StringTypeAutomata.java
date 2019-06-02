package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.StringTypeNode;

public class StringTypeAutomata extends TerminalNodeParser {

    public StringTypeAutomata() {
        super(TokenType.STRING_TYPE, token -> new StringTypeNode(token.getLine(), token.getStartColumn()));
    }
}

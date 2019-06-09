package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.StringTypeNode;

public class StringTypeAutomata extends TerminalNodeAutomata {

    public StringTypeAutomata() {
        super(
                Rule.STRING_TYPE,
                TokenType.STRING_TYPE,
                token -> new StringTypeNode(token.getLine(), token.getStartColumn())
        );
    }
}

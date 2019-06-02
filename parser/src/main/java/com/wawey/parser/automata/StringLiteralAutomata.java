package com.wawey.parser.automata;

import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.StringLiteralNode;

public class StringLiteralAutomata extends TerminalNodeParser {

    public StringLiteralAutomata() {
        super(
                Rule.STRING_LITERAL,
                TokenType.STRING_LITERAL,
                token -> new StringLiteralNode(token.getLine(), token.getStartColumn(), token.getLexeme())
        );
    }
}

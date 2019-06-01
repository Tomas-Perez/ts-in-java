package com.wawey.parser.ast;

import com.wawey.parser.Rule;

public class IdentifierNode extends TerminalNode{
    public IdentifierNode(int line, int startColumn, String value) {
        super(Rule.IDENTIFIER, line, startColumn, value);
    }
}

package com.wawey.parser.ast;

import com.wawey.parser.Rule;

public class NumberTypeNode extends TerminalNode{
    public NumberTypeNode(int line, int startColumn) {
        super(Rule.NUMBER_TYPE, line, startColumn, "number");
    }
}

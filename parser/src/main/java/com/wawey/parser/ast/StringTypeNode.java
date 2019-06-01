package com.wawey.parser.ast;

import com.wawey.parser.Rule;

public class StringTypeNode extends TerminalNode{
    public StringTypeNode(int line, int startColumn) {
        super(Rule.STRING_TYPE, line, startColumn, "string");
    }
}

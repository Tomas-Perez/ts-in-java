package com.wawey.parser.ast;

import com.wawey.parser.Rule;

public class StringLiteralNode extends TerminalNode {
    public StringLiteralNode(int line, int startColumn, String value) {
        super(Rule.STRING_LITERAL, line, startColumn, value);
    }
}

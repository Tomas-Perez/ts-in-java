package com.wawey.parser.ast;

import com.wawey.parser.Rule;

public class NumberLiteralNode extends TerminalNode {
    public NumberLiteralNode(int line, int startColumn, String value) {
        super(Rule.NUMBER_LITERAL, line, startColumn, value);
    }
}

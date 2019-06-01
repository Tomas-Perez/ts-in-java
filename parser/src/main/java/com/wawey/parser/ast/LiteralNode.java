package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;
import java.util.List;

public class LiteralNode extends NonTerminalNode{
    public LiteralNode(NumberLiteralNode numberLiteral) {
        super(Rule.LITERAL, Collections.singletonList(numberLiteral));
    }

    public LiteralNode(StringLiteralNode stringLiteral) {
        super(Rule.LITERAL, Collections.singletonList(stringLiteral));
    }
}

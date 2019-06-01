package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class InitializerNode extends NonTerminalNode {
    public InitializerNode(AdditiveExpressionNode additiveExpression) {
        super(Rule.INITIALIZER, Collections.singletonList(additiveExpression));
    }
}

package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class PrimaryExpressionNode extends NonTerminalNode {
    public PrimaryExpressionNode(LiteralNode literal) {
        super(Rule.PRIMARY_EXPRESSION, Collections.singletonList(literal));
    }

    public PrimaryExpressionNode(IdentifierNode identifier) {
        super(Rule.PRIMARY_EXPRESSION, Collections.singletonList(identifier));
    }

    public PrimaryExpressionNode(AdditiveExpressionNode additiveExpression) {
        super(Rule.PRIMARY_EXPRESSION, Collections.singletonList(additiveExpression));
    }
}

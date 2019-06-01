package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class MultiplicativeExpressionNode extends NonTerminalNode {
    public MultiplicativeExpressionNode(PrimaryExpressionNode primaryExpression) {
        super(Rule.MULTIPLICATIVE_EXPRESSION, Collections.singletonList(primaryExpression));
    }

    public MultiplicativeExpressionNode(MultiplyExpressionNode multiplyExpression) {
        super(Rule.MULTIPLICATIVE_EXPRESSION, Collections.singletonList(multiplyExpression));
    }

    public MultiplicativeExpressionNode(DivideExpressionNode divideExpression) {
        super(Rule.MULTIPLICATIVE_EXPRESSION, Collections.singletonList(divideExpression));
    }
}

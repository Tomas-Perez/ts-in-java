package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Arrays;

public class MultiplyExpressionNode extends NonTerminalNode {
    public MultiplyExpressionNode(MultiplicativeExpressionNode multiplicativeExpression, PrimaryExpressionNode primaryExpression) {
        super(Rule.MULTIPLY_EXPRESSION, Arrays.asList(multiplicativeExpression, primaryExpression));
    }
}

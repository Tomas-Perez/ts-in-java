package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Arrays;

public class DivideExpressionNode extends NonTerminalNode {
    public DivideExpressionNode(MultiplicativeExpressionNode multiplicativeExpression, PrimaryExpressionNode primaryExpression) {
        super(Rule.DIVIDE_EXPRESSION, Arrays.asList(multiplicativeExpression, primaryExpression));
    }
}

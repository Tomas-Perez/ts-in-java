package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Arrays;

public class SumExpression extends NonTerminalNode{
    public SumExpression(AdditiveExpressionNode additiveExpression, MultiplicativeExpressionNode multiplicativeExpression) {
        super(Rule.SUM_EXPRESSION, Arrays.asList(additiveExpression, multiplicativeExpression));
    }
}

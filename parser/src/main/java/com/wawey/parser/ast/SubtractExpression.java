package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Arrays;

public class SubtractExpression extends NonTerminalNode{
    public SubtractExpression(AdditiveExpressionNode additiveExpression, MultiplicativeExpressionNode multiplicativeExpression) {
        super(Rule.SUBTRACT_EXPRESSION, Arrays.asList(additiveExpression, multiplicativeExpression));
    }
}

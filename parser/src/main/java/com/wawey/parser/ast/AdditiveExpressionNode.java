package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class AdditiveExpressionNode extends NonTerminalNode {
    public AdditiveExpressionNode(MultiplicativeExpressionNode multiplicativeExpression) {
        super(Rule.ADDITIVE_EXPRESSION, Collections.singletonList(multiplicativeExpression));
    }

    public AdditiveExpressionNode(SumExpression sum) {
        super(Rule.ADDITIVE_EXPRESSION, Collections.singletonList(sum));
    }

    public AdditiveExpressionNode(SubtractExpression subtract) {
        super(Rule.ADDITIVE_EXPRESSION, Collections.singletonList(subtract));
    }
}

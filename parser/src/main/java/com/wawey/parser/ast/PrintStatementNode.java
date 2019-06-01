package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class PrintStatementNode extends NonTerminalNode {
    public PrintStatementNode(AdditiveExpressionNode additiveExpression) {
        super(Rule.PRINT_STATEMENT, Collections.singletonList(additiveExpression));
    }
}

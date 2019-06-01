package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class LineNode extends NonTerminalNode {
    public LineNode(StatementNode statement) {
        super(Rule.LINE, Collections.singletonList(statement));
    }
}

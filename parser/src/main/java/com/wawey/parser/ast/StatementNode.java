package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class StatementNode extends NonTerminalNode {

    public StatementNode(VariableDeclarationNode variableDeclaration) {
        super(Rule.STATEMENT, Collections.singletonList(variableDeclaration));
    }

    public StatementNode(PrintStatementNode printStatement) {
        super(Rule.STATEMENT, Collections.singletonList(printStatement));
    }

    public StatementNode(CoverInitializedNameNode coverInitializedName) {
        super(Rule.STATEMENT, Collections.singletonList(coverInitializedName));
    }
}

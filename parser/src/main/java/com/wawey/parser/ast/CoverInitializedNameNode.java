package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Arrays;

public class CoverInitializedNameNode extends NonTerminalNode {
    public CoverInitializedNameNode(IdentifierNode identifier, InitializerNode initializer) {
        super(Rule.COVER_INITIALIZED_NAME, Arrays.asList(identifier, initializer));
    }
}

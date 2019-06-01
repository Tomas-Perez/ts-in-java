package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class TypeNode extends NonTerminalNode{
    public TypeNode(NumberTypeNode numberType) {
        super(Rule.TYPE, Collections.singletonList(numberType));
    }
    public TypeNode(StringTypeNode numberType) {
        super(Rule.TYPE, Collections.singletonList(numberType));
    }
}

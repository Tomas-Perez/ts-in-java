package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Collections;

public class TypeAnnotationNode extends NonTerminalNode {
    public TypeAnnotationNode(TypeNode type) {
        super(Rule.TYPE_ANNOTATION, Collections.singletonList(type));
    }
}

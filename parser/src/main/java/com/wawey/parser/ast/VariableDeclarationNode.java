package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Arrays;
import java.util.Collections;

public class VariableDeclarationNode extends NonTerminalNode {
    public VariableDeclarationNode(IdentifierNode identifier) {
        super(Rule.VARIABLE_DECLARATION, Collections.singletonList(identifier));
    }

    public VariableDeclarationNode(IdentifierNode identifier, TypeAnnotationNode typeAnnotation) {
        super(Rule.VARIABLE_DECLARATION, Arrays.asList(identifier, typeAnnotation));
    }

    public VariableDeclarationNode(IdentifierNode identifier, InitializerNode initializer) {
        super(Rule.VARIABLE_DECLARATION, Arrays.asList(identifier, initializer));
    }

    public VariableDeclarationNode(IdentifierNode identifier, TypeAnnotationNode typeAnnotation, InitializerNode initializer) {
        super(Rule.VARIABLE_DECLARATION, Arrays.asList(identifier, typeAnnotation, initializer));
    }
}

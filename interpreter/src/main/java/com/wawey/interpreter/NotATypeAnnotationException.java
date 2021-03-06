package com.wawey.interpreter;

import com.wawey.parser.ast.ASTNode;

/**
 * @author Tomas Perez Molina
 */
public class NotATypeAnnotationException extends RuntimeException {
    public NotATypeAnnotationException(ASTNode node) {
        super(String.format("\"%s\" is not part of a type annotation", node));
    }
}

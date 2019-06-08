package com.wawey.interpreter;

import com.wawey.parser.ast.ASTNode;

/**
 * @author Tomas Perez Molina
 */
public class NotAnExpressionException extends RuntimeException {
    public NotAnExpressionException(ASTNode node) {
        super(String.format("\"%s\" is not part of an expression", node));
    }
}

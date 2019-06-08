package com.wawey.interpreter;

import com.wawey.parser.ast.ASTNode;

/**
 * @author Tomas Perez Molina
 */
public interface Interpreter {
    void interpret(ASTNode ast);
}

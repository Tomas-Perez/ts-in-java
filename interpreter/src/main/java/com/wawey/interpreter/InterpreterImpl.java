package com.wawey.interpreter;

import com.wawey.parser.ast.ASTNode;

/**
 * @author Tomas Perez Molina
 */
public class InterpreterImpl implements Interpreter{
    private final InterpreterVisitor visitor;

    public InterpreterImpl(Printer printer) {
        ExecutionContext context = new ExecutionContext();
        visitor = new InterpreterVisitor(context, printer, new ExpressionVisitor(context), new TypeAnnotationVisitor());
    }

    @Override
    public void interpret(ASTNode ast) {
        ast.accept(visitor);
    }
}

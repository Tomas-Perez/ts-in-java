package com.wawey.interpreter;

import com.wawey.parser.ast.ASTVisitor;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.TerminalNode;

/**
 * @author Tomas Perez Molina
 */
public class TypeAnnotationVisitor implements ASTVisitor<VariableType> {
    @Override
    public VariableType visit(NonTerminalNode nonTerminal) {
        switch (nonTerminal.getRule()) {
            case TYPE:
            case TYPE_ANNOTATION:
                return nonTerminal.getChildren().get(0).accept(this);
            default:
                throw new NotATypeAnnotationException(nonTerminal);
        }
    }

    @Override
    public VariableType visit(TerminalNode terminal) {
        switch (terminal.getRule()) {
            case NUMBER_TYPE:
                return VariableType.NUMBER;
            case STRING_TYPE:
                return VariableType.STRING;
            default:
                throw new NotATypeAnnotationException(terminal);
        }
    }
}

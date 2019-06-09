package com.wawey.interpreter;

import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.ASTVisitor;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.TerminalNode;

import java.util.List;

/**
 * @author Tomas Perez Molina
 */
public class InterpreterVisitor implements ASTVisitor<Void> {
    private final VariableDeclarator variableDeclarator;
    private final ASTVisitor<Value> expressionVisitor;
    private final ASTVisitor<VariableType> typeAnnotationVisitor;
    private final Printer printer;

    public InterpreterVisitor(VariableDeclarator variableDeclarator, Printer printer, ASTVisitor<Value> expressionVisitor, ASTVisitor<VariableType> typeAnnotationVisitor) {
        this.variableDeclarator = variableDeclarator;
        this.printer = printer;
        this.expressionVisitor = expressionVisitor;
        this.typeAnnotationVisitor = typeAnnotationVisitor;
    }

    @Override
    public Void visit(NonTerminalNode nonTerminal) {
        List<ASTNode> children = nonTerminal.getChildren();
        switch (nonTerminal.getRule()) {
            case STATEMENT:
            case LINE:
            case PROGRAM:
                children.get(0).accept(this);
                if (children.size() == 2) {
                    children.get(1).accept(this);
                }
                break;
            case FILE:
                children.get(0).accept(this);
                break;
            case PRINT_STATEMENT:
                printer.print(children.get(0).accept(expressionVisitor).getString());
                break;
            case COVER_INITIALIZED_NAME: {
                try {
                    String identifier = ((TerminalNode) children.get(0)).getValue();
                    NonTerminalNode initializer = ((NonTerminalNode) children.get(1));
                    variableDeclarator.setVariableValue(identifier, initializer.getChildren().get(0).accept(expressionVisitor));
                } catch (InterpreterException exc) {
                    throw new LocatedInterpreterException(
                            exc,
                            nonTerminal.getStartLine(),
                            nonTerminal.getColumnRanges().get(0).getColumnRange().getStart()
                    );
                }
                break;
            }
            case VARIABLE_DECLARATION:
                try {
                    String identifier = ((TerminalNode) children.get(0)).getValue();
                    if (children.size() == 1) {
                        variableDeclarator.declareVariable(identifier);
                    } else if (children.get(1).getRule() == Rule.INITIALIZER) {
                        NonTerminalNode initializer = ((NonTerminalNode) children.get(1));
                        Value value = initializer.getChildren().get(0).accept(expressionVisitor);
                        variableDeclarator.declareVariable(identifier);
                        variableDeclarator.setVariableValue(identifier, value);
                    } else {
                        NonTerminalNode typeAnnotation = ((NonTerminalNode) children.get(1));
                        variableDeclarator.declareVariable(identifier, typeAnnotation.accept(typeAnnotationVisitor));
                        if (children.size() == 3) {
                            NonTerminalNode initializer = ((NonTerminalNode) children.get(2));
                            Value value = initializer.getChildren().get(0).accept(expressionVisitor);
                            variableDeclarator.setVariableValue(identifier, value);
                        }
                    }
                } catch (InterpreterException exc) {
                    throw new LocatedInterpreterException(
                            exc,
                            nonTerminal.getStartLine(),
                            nonTerminal.getColumnRanges().get(0).getColumnRange().getStart()
                    );
                }
                break;
            default:
                throw new MalformedASTException();
        }
        return null;
    }

    @Override
    public Void visit(TerminalNode terminal) {
        throw new MalformedASTException();
    }
}

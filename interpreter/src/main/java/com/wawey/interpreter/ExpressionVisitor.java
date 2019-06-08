package com.wawey.interpreter;

import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.ASTVisitor;
import com.wawey.parser.ast.NonTerminalNode;
import com.wawey.parser.ast.TerminalNode;

/**
 * @author Tomas Perez Molina
 */
public class ExpressionVisitor implements ASTVisitor<Value> {
    private final VariablePool pool;

    public ExpressionVisitor(VariablePool pool) {
        this.pool = pool;
    }

    @Override
    public Value visit(NonTerminalNode nonTerminal) {
        switch (nonTerminal.getRule()) {
            case LITERAL:
            case IDENTIFIER:
            case PRIMARY_EXPRESSION:
            case MULTIPLICATIVE_EXPRESSION:
            case ADDITIVE_EXPRESSION:
                return nonTerminal.getChildren().get(0).accept(this);
            case SUM_EXPRESSION: {
                ASTNode left = nonTerminal.getChildren().get(0);
                ASTNode right = nonTerminal.getChildren().get(1);
                return left.accept(this).sum(right.accept(this));
            }
            case SUBTRACT_EXPRESSION: {
                ASTNode left = nonTerminal.getChildren().get(0);
                ASTNode right = nonTerminal.getChildren().get(1);
                return left.accept(this).subtract(right.accept(this));
            }
            case MULTIPLY_EXPRESSION: {
                ASTNode left = nonTerminal.getChildren().get(0);
                ASTNode right = nonTerminal.getChildren().get(1);
                return left.accept(this).multiply(right.accept(this));
            }
            case DIVIDE_EXPRESSION: {
                ASTNode left = nonTerminal.getChildren().get(0);
                ASTNode right = nonTerminal.getChildren().get(1);
                return left.accept(this).divide(right.accept(this));
            }
            default:
                throw new NotAnExpressionException(nonTerminal);
        }
    }

    @Override
    public Value visit(TerminalNode terminal) {
        switch (terminal.getRule()) {
            case IDENTIFIER:
                return pool.getVariable(terminal.getValue());
            case NUMBER_LITERAL:
                return new NumberValue(Double.parseDouble(terminal.getValue()));
            case STRING_LITERAL:
                String value = terminal.getValue();
                return new StringValue(value.substring(1, value.length() - 1));
            default:
                throw new NotAnExpressionException(terminal);
        }
    }
}

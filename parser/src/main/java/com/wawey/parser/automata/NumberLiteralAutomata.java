package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.LiteralNode;
import com.wawey.parser.ast.NumberLiteralNode;

import java.util.Stack;

public class NumberLiteralAutomata implements ParserAutomata {
    private Stack<ASTNode> stack = new Stack<>();
    private ParserAutomataState currentState;

    public NumberLiteralAutomata() {
        this.currentState = new InitialState();
    }

    @Override
    public void consume(Token token) {
        currentState = currentState.transition(token, stack);
    }

    @Override
    public ASTNode getResult() {
        return stack.peek();
    }

    @Override
    public void reset() {
        currentState = new InitialState();
    }

    @Override
    public boolean acceptable() {
        return currentState.isAcceptable();
    }

    @Override
    public boolean accepts(Token token) {
        return currentState.accepts(token);
    }

    private class InitialState implements ParserAutomataState {
        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.NUMBER_LITERAL;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                stack.push(
                        new NumberLiteralNode(token.getLine(), token.getStartColumn(), token.getLexeme())
                );
                return new AcceptedState();
            } else {
                throw new NoTransitionException();
            }

        }
    }

    private class AcceptedState implements ParserAutomataState {
        @Override
        public boolean isAcceptable() {
            return true;
        }

        @Override
        public boolean accepts(Token token) {
            return false;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            throw new NoTransitionException();
        }
    }
}

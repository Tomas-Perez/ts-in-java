package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class LiteralAutomata implements ParserAutomata {
    private final Stack<ASTNode> stack = new Stack<>();
    private ParserAutomataState currentState;

    public LiteralAutomata() {
        this.currentState = new InitialState();
    }

    @Override
    public void consume(Token token) {
        currentState = currentState.transition(token, stack);
    }

    @Override
    public boolean acceptable() {
        return currentState.isAcceptable();
    }

    @Override
    public boolean accepts(Token token) {
        return currentState.accepts(token);
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.LITERAL, Collections.singletonList(stack.peek()));
    }

    @Override
    public void reset() {
        currentState = new InitialState();
    }

    private class InitialState implements ParserAutomataState {
        private List<Transition> transitions = Arrays.asList(
                new Transition() {
                    private ParserAutomata inner = new NumberLiteralAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, AcceptanceState::new);
                        return next.transition(token, stack);
                    }
                },
                new Transition() {
                    private ParserAutomata inner = new StringLiteralAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, AcceptanceState::new);
                        return next.transition(token, stack);
                    }
                }
        );

        @Override
        public boolean accepts(Token token) {
            return transitions.stream().anyMatch(t -> t.consumes(token));
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            return transitions.stream()
                    .filter(t -> t.consumes(token))
                    .findFirst()
                    .map(t -> t.nextState(token, stack))
                    .orElseThrow(NoTransitionException::new);
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }
    }

    private class AcceptanceState implements ParserAutomataState {
        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            throw new NoTransitionException();
        }

        @Override
        public boolean accepts(Token token) {
            return false;
        }

        @Override
        public boolean isAcceptable() {
            return true;
        }
    }
}

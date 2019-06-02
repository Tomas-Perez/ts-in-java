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

public class PrimaryExpressionAutomata extends ParserAutomataImpl {

    public PrimaryExpressionAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.PRIMARY_EXPRESSION, stack.peek());
    }

    private static class InitialState implements ParserAutomataState {
        private List<Transition> transitions = Arrays.asList(
                new Transition() {
                    ParserAutomata inner = new LiteralAutomata();

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
                    ParserAutomata inner = new IdentifierAutomata();

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
                    @Override
                    public boolean consumes(Token token) {
                        return token.getType() == TokenType.LEFT_PAREN;
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        return new InnerAutomataState(new IdentifierAutomata(), RightParenState::new);
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

    private static class AcceptanceState implements ParserAutomataState {
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

    private static class RightParenState implements ParserAutomataState {
        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                return new AcceptanceState();
            } else {
                throw new NoTransitionException();
            }
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.RIGHT_PAREN;
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }
    }
}

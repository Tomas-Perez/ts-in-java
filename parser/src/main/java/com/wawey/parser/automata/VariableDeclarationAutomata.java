package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.*;

/**
 * @author Tomas Perez Molina
 */
public class VariableDeclarationAutomata extends ParserAutomataImpl {
    public VariableDeclarationAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        List<ASTNode> result = new LinkedList<>(stack);
        return new NonTerminalNode(Rule.VARIABLE_DECLARATION, result);
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                return new InnerAutomataState(new IdentifierAutomata(), PostIdentifierState::new);
            } else throw new NoTransitionException();
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.LET;
        }
    }

    private static class PostIdentifierState implements ParserAutomataState {
        private List<Transition> transitions = Arrays.asList(
                new Transition() {
                    private ParserAutomata inner = new TypeAnnotationAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, PostTypeAnnotationState::new);
                        return next.transition(token, stack);
                    }
                },
                new Transition() {
                    private ParserAutomata inner = new InitializerAutomata();

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
            return true;
        }
    }

    private static class PostTypeAnnotationState implements ParserAutomataState {
        private List<Transition> transitions = Collections.singletonList(
                new Transition() {
                    private ParserAutomata inner = new InitializerAutomata();

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
}

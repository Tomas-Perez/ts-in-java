package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class LineAutomata extends ParserAutomataImpl {
    public LineAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.LINE, stack.peek());
    }

    private static class InitialState implements ParserAutomataState {
        private List<Transition> transitions = Collections.singletonList(
                new Transition() {
                    ParserAutomata inner = new StatementAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, GotStatementState::new);
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

    private static class GotStatementState implements ParserAutomataState {
        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                return new AcceptedState();
            } else throw new NoTransitionException();
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.SEMICOLON;
        }
    }

    private static class AcceptedState implements ParserAutomataState {
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

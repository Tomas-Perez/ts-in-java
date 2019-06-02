package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class StatementAutomata extends ParserAutomataImpl {
    public StatementAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.STATEMENT, stack.peek());
    }

    private static class InitialState implements ParserAutomataState {
        private List<Transition> transitions = Arrays.asList(
                new Transition() {
                    ParserAutomata inner = new VariableDeclarationAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, AcceptedState::new);
                        return next.transition(token, stack);
                    }
                },
                new Transition() {
                    ParserAutomata inner = new PrintStatementAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, AcceptedState::new);
                        return next.transition(token, stack);
                    }
                },
                new Transition() {
                    ParserAutomata inner = new CoverInitializedNameAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, AcceptedState::new);
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
}

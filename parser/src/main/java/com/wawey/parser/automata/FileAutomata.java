package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class FileAutomata extends ParserAutomataImpl {
    public FileAutomata() {
        super(new InitialState());
    }

    private static class InitialState implements ParserAutomataState {
        private List<Transition> transitions = Collections.singletonList(
                new Transition() {
                    ParserAutomata inner = new LineAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, GotLineState::new, (s) -> {
                            ASTNode line = s.pop();
                            s.push(new NonTerminalNode(Rule.FILE, line));
                        });
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

    private static class GotLineState implements ParserAutomataState {
        private List<Transition> transitions = Collections.singletonList(
                new Transition() {
                    ParserAutomata inner = new LineAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, GotLineState::new, (s) -> {
                            ASTNode line = s.pop();
                            ASTNode file = s.pop();
                            s.push(new NonTerminalNode(Rule.FILE, file, line));
                        });
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
}

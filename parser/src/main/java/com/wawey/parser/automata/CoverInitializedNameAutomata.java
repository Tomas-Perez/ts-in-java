package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class CoverInitializedNameAutomata extends ParserAutomataImpl {
    public CoverInitializedNameAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        List<ASTNode> result = new LinkedList<>(stack);
        return new NonTerminalNode(Rule.COVER_INITIALIZED_NAME, result);
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new Transition() {
                        ParserAutomata inner = new IdentifierAutomata();

                        @Override
                        public boolean consumes(Token token) {
                            return inner.accepts(token);
                        }

                        @Override
                        public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                            ParserAutomataState next = new InnerAutomataState(inner, GotIdentifierState::new);
                            return next.transition(token, stack);
                        }
                    }
            );
        }
    }

    private static class GotIdentifierState extends TransitionState {
        public GotIdentifierState() {
            super(
                    new Transition() {
                        ParserAutomata inner = new InitializerAutomata();

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
        }
    }
}

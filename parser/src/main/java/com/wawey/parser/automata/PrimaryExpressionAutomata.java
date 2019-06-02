package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

public class PrimaryExpressionAutomata extends ParserAutomataImpl {

    public PrimaryExpressionAutomata() {
        super(Rule.PRIMARY_EXPRESSION, new InitialState());
    }

    private static class InitialState extends TransitionState {

        public InitialState() {
            super(
                    new TransitionToAutomata(new LiteralAutomata(), AcceptedState::new),
                    new TransitionToAutomata(new IdentifierAutomata(), AcceptedState::new),
                    new Transition() {
                        @Override
                        public boolean consumes(Token token) {
                            return token.getType() == TokenType.LEFT_PAREN;
                        }

                        @Override
                        public StateChange nextState(Token token, Stack<ASTNode> stack) {
                            return new StateChangeImpl(
                                    new InnerAutomataState(new AdditiveExpressionAutomata(), RightParenState::new),
                                    stack
                            );
                        }
                    }
            );
        }
    }

    private static class RightParenState implements ParserAutomataState {
        @Override
        public StateChange transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                return new StateChangeImpl(
                        new AcceptedState(),
                        stack
                );
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

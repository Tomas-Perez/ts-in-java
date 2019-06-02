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
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.PRIMARY_EXPRESSION, stack.peek());
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
                        public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                            return new InnerAutomataState(new AdditiveExpressionAutomata(), RightParenState::new);
                        }
                    }
            );
        }
    }

    private static class RightParenState implements ParserAutomataState {
        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                return new AcceptedState();
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

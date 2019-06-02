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

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
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
}

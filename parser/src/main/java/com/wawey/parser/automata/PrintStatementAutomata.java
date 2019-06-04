package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;

/**
 * @author Tomas Perez Molina
 */
public class PrintStatementAutomata extends ParserAutomataImpl {
    public PrintStatementAutomata() {
        super(Rule.PRINT_STATEMENT, new InitialState());
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public StateChange transition(Token token, ImmutableStack<ASTNode> stack) {
            if (accepts(token)) {
                return new StateChangeImpl(
                        new PostPrintState(),
                        stack
                );
            } else throw new NoTransitionException();
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.PRINT;
        }
    }

    private static class PostPrintState implements ParserAutomataState {
        @Override
        public StateChange transition(Token token, ImmutableStack<ASTNode> stack) {
            if (accepts(token)) {
                return new StateChangeImpl(
                        new InnerAutomataState(new AdditiveExpressionAutomata(), RightParenState::new),
                        stack
                );
            } else throw new NoTransitionException();
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.LEFT_PAREN;
        }
    }

    private static class RightParenState implements ParserAutomataState {
        @Override
        public StateChangeImpl transition(Token token, ImmutableStack<ASTNode> stack) {
            if (accepts(token)) {
                return new StateChangeImpl(
                        new AcceptedState(),
                        stack
                );
            } else throw new NoTransitionException();
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.RIGHT_PAREN;
        }
    }
}

package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.IdentifierNode;

import java.util.Stack;

public class IdentifierAutomata extends ParserAutomataImpl {

    public IdentifierAutomata() {
        super(new InitialState());
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.IDENTIFIER;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                stack.push(
                        new IdentifierNode(token.getLine(), token.getStartColumn(), token.getLexeme())
                );
                return new AcceptedState();
            } else {
                throw new NoTransitionException();
            }
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

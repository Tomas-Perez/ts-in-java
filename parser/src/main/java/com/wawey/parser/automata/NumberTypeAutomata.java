package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NumberLiteralNode;
import com.wawey.parser.ast.NumberTypeNode;

import java.util.Stack;

public class NumberTypeAutomata extends ParserAutomataImpl {

    public NumberTypeAutomata() {
        super(new InitialState());
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.NUMBER_TYPE;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                stack.push(
                        new NumberTypeNode(token.getLine(), token.getStartColumn())
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

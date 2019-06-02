package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NumberTypeNode;
import com.wawey.parser.ast.StringTypeNode;

import java.util.Stack;

public class StringTypeAutomata extends ParserAutomataImpl {

    public StringTypeAutomata() {
        super(new InitialState());
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.STRING_TYPE;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                stack.push(
                        new StringTypeNode(token.getLine(), token.getStartColumn())
                );
                return new AcceptedState();
            } else {
                throw new NoTransitionException();
            }

        }
    }
}

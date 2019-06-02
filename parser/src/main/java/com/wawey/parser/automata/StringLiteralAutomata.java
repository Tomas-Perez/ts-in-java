package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.StringLiteralNode;

import java.util.Stack;

public class StringLiteralAutomata extends ParserAutomataImpl {

    public StringLiteralAutomata() {
        super(new InitialState());
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.STRING_LITERAL;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                stack.push(
                        new StringLiteralNode(token.getLine(), token.getStartColumn(), token.getLexeme())
                );
                return new AcceptedState();
            } else {
                throw new NoTransitionException();
            }
        }
    }
}

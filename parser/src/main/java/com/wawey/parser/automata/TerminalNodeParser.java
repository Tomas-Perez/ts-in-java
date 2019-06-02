package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;
import java.util.function.Function;

/**
 * @author Tomas Perez Molina
 */
public class TerminalNodeParser extends ParserAutomataImpl {

    public TerminalNodeParser(TokenType tokenType, Function<Token, ASTNode> nodeMapper) {
        super(new InitialState(tokenType, nodeMapper));
    }

    private static class InitialState implements ParserAutomataState {
        private final TokenType tokenType;
        private final Function<Token, ASTNode> nodeMapper;

        InitialState(TokenType tokenType, Function<Token, ASTNode> nodeMapper) {
            this.tokenType = tokenType;
            this.nodeMapper = nodeMapper;
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == tokenType;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                stack.push(nodeMapper.apply(token));
                return new AcceptedState();
            } else {
                throw new NoTransitionException();
            }
        }
    }
}

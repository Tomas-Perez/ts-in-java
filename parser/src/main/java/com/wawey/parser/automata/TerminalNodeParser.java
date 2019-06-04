package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;
import java.util.function.Function;

/**
 * @author Tomas Perez Molina
 */
public class TerminalNodeParser extends ParserAutomataImpl {

    public TerminalNodeParser(Rule rule, TokenType tokenType, Function<Token, ASTNode> nodeMapper) {
        super(rule, new InitialState(tokenType, nodeMapper));
    }

    @Override
    public ASTNode getResult() {
        return stack.peek();
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
        public StateChangeImpl transition(Token token, ImmutableStack<ASTNode> stack) {
            if (accepts(token)) {
                return new StateChangeImpl(
                        new AcceptedState(),
                        stack.push(nodeMapper.apply(token))
                );
            } else {
                throw new NoTransitionException();
            }
        }
    }
}

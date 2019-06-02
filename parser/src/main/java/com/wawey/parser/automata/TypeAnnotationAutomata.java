package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class TypeAnnotationAutomata extends ParserAutomataImpl {
    public TypeAnnotationAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.TYPE_ANNOTATION, stack.peek());
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                return new InnerAutomataState(new TypeAutomata(), AcceptedState::new);
            } else throw new NoTransitionException();
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.COLON;
        }
    }
}

package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class VariableDeclarationAutomata extends ParserAutomataImpl {
    public VariableDeclarationAutomata() {
        super(Rule.VARIABLE_DECLARATION, new InitialState());
    }

    private static class InitialState implements ParserAutomataState {
        @Override
        public StateChange transition(Token token, Stack<ASTNode> stack) {
            if (accepts(token)) {
                return new StateChangeImpl(
                        new InnerAutomataState(new IdentifierAutomata(), PostIdentifierState::new),
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
            return token.getType() == TokenType.LET;
        }
    }

    private static class PostIdentifierState extends TransitionState {
        public PostIdentifierState() {
            super(
                    true,
                    new TransitionToAutomata(new TypeAnnotationAutomata(), PostTypeAnnotationState::new),
                    new TransitionToAutomata(new InitializerAutomata(), AcceptedState::new)
            );
        }
    }

    private static class PostTypeAnnotationState extends TransitionState {
        public PostTypeAnnotationState() {
            super(new TransitionToAutomata(new InitializerAutomata(), AcceptedState::new));
        }
    }
}

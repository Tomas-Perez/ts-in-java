package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;

public class FileAutomata extends ParserAutomataImpl {
    public FileAutomata() {
        super(Rule.FILE, new InitialState());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new TransitionToAutomata(new ProgramAutomata(), GotFileState::new)
            );
        }
    }

    private static class GotFileState implements ParserAutomataState {
        @Override
        public StateChange transition(Token token, ImmutableStack<ASTNode> stack) {
            if (accepts(token)) {
                return new StateChangeImpl(
                        new AcceptedState(),
                        stack
                );
            } else throw new NoTransitionException(token);
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }

        @Override
        public boolean accepts(Token token) {
            return token.getType() == TokenType.EOF;
        }
    }
}

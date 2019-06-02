package com.wawey.parser.automata;

import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tomas Perez Molina
 */
public class CoverInitializedNameAutomata extends ParserAutomataImpl {
    public CoverInitializedNameAutomata() {
        super(Rule.COVER_INITIALIZED_NAME, new InitialState());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(new TransitionToAutomata(new IdentifierAutomata(), GotIdentifierState::new));
        }
    }

    private static class GotIdentifierState extends TransitionState {
        public GotIdentifierState() {
            super(new TransitionToAutomata(new InitializerAutomata(), AcceptedState::new));
        }
    }
}

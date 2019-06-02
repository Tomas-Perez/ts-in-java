package com.wawey.parser.automata;

import com.wawey.parser.Rule;

/**
 * @author Tomas Perez Molina
 */
public class StatementAutomata extends ParserAutomataImpl {
    public StatementAutomata() {
        super(Rule.STATEMENT, new InitialState());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new TransitionToAutomata(new VariableDeclarationAutomata(), AcceptedState::new),
                    new TransitionToAutomata(new PrintStatementAutomata(), AcceptedState::new),
                    new TransitionToAutomata(new CoverInitializedNameAutomata(), AcceptedState::new)
            );
        }
    }
}

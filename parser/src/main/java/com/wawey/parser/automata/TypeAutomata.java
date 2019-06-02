package com.wawey.parser.automata;

import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

public class TypeAutomata extends ParserAutomataImpl {

    public TypeAutomata() {
        super(Rule.TYPE, new InitialState());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new TransitionToAutomata(new NumberTypeAutomata(), AcceptedState::new),
                    new TransitionToAutomata(new StringTypeAutomata(), AcceptedState::new)
            );
        }
    }
}

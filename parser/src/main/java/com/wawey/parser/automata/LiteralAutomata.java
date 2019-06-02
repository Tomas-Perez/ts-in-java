package com.wawey.parser.automata;

import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

public class LiteralAutomata extends ParserAutomataImpl {

    public LiteralAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.LITERAL, stack.peek());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new TransitionToAutomata(new NumberLiteralAutomata(), AcceptedState::new),
                    new TransitionToAutomata(new StringLiteralAutomata(), AcceptedState::new)
            );
        }
    }
}

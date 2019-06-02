package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class CoverInitializedNameAutomata extends ParserAutomataImpl {
    public CoverInitializedNameAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        List<ASTNode> result = new LinkedList<>(stack);
        return new NonTerminalNode(Rule.COVER_INITIALIZED_NAME, result);
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

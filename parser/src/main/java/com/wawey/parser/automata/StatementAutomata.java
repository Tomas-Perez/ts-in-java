package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class StatementAutomata extends ParserAutomataImpl {
    public StatementAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.STATEMENT, stack.peek());
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

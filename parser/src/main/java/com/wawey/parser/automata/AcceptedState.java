package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

/**
 * @author Tomas Perez Molina
 */
public class AcceptedState implements ParserAutomataState {
    @Override
    public boolean isAcceptable() {
        return true;
    }

    @Override
    public boolean accepts(Token token) {
        return false;
    }

    @Override
    public StateChange transition(Token token, ImmutableStack<ASTNode> stack) {
        throw new NoTransitionException();
    }
}

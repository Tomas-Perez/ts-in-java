package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

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
    public StateChange transition(Token token, Stack<ASTNode> stack) {
        throw new NoTransitionException();
    }
}

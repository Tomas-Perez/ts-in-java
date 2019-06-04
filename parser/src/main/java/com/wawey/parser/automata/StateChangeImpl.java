package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class StateChangeImpl implements StateChange{
    private final ParserAutomataState nextState;
    private final ImmutableStack<ASTNode> newStack;

    public StateChangeImpl(ParserAutomataState nextState, ImmutableStack<ASTNode> newStack) {
        this.nextState = nextState;
        this.newStack = newStack;
    }

    @Override
    public ParserAutomataState getNextState() {
        return nextState;
    }

    @Override
    public ImmutableStack<ASTNode> getNewStack() {
        return newStack;
    }
}

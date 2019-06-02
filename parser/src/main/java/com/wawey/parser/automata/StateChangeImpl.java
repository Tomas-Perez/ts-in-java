package com.wawey.parser.automata;

import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class StateChangeImpl implements StateChange{
    private final ParserAutomataState nextState;
    private final Stack<ASTNode> newStack;

    public StateChangeImpl(ParserAutomataState nextState, Stack<ASTNode> newStack) {
        this.nextState = nextState;
        this.newStack = newStack;
    }

    @Override
    public ParserAutomataState getNextState() {
        return nextState;
    }

    @Override
    public Stack<ASTNode> getNewStack() {
        return newStack;
    }
}

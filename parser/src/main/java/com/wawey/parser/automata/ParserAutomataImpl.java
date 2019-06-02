package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

public class ParserAutomataImpl implements ParserAutomata {
    protected Stack<ASTNode> stack = new Stack<>();
    private ParserAutomataState currentState;
    private final ParserAutomataState initialState;

    public ParserAutomataImpl(ParserAutomataState initialState) {
        this.initialState = initialState;
        this.currentState = initialState;
    }

    @Override
    public void consume(Token token) {
        StateChange change = currentState.transition(token, stack);
        currentState = change.getNextState();
        stack = change.getNewStack();
    }

    @Override
    public ASTNode getResult() {
        return stack.peek();
    }

    @Override
    public void reset() {
        currentState = initialState;
    }

    @Override
    public boolean accepts(Token token) {
        return currentState.accepts(token);
    }

    @Override
    public boolean acceptable() {
        return currentState.isAcceptable();
    }
}

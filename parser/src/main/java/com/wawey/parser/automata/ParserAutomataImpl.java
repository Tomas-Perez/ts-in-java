package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.LinkedList;
import java.util.Stack;

public class ParserAutomataImpl implements ParserAutomata {
    private final Rule rule;
    protected Stack<ASTNode> stack = new Stack<>();
    private ParserAutomataState currentState;
    private final ParserAutomataState initialState;

    public ParserAutomataImpl(Rule rule, ParserAutomataState initialState) {
        this.rule = rule;
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
        return new NonTerminalNode(rule, new LinkedList<>(stack));
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

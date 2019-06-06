package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.helper.LinkedImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.LinkedList;

public class ParserAutomataImpl implements ParserAutomata {
    private final Rule rule;
    protected ImmutableStack<ASTNode> stack = LinkedImmutableStack.empty();
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
        return new NonTerminalNode(rule, new LinkedList<>(stack.toList()));
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

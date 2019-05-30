package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class AutomataImpl implements Automata {
    private final AutomataState initialState;
    private AutomataState currentState;

    public AutomataImpl(AutomataState initialState) {
        this.initialState = initialState;
        this.currentState = initialState;
    }

    @Override
    public boolean acceptable() {
        return currentState.isAcceptable();
    }

    @Override
    public void consume(char c) {
        currentState = currentState.transition(c);
    }

    @Override
    public void reset() {
        this.currentState = initialState;
    }
}

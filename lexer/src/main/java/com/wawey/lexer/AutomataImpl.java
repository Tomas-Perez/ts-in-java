package com.wawey.lexer;

/**
 * @author Tomas Perez Molina
 */
public class AutomataImpl implements Automata {
    private AutomataState currentState;

    public AutomataImpl(AutomataState currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean acceptable() {
        return currentState.isAcceptable();
    }

    @Override
    public void consume(char c) {
        currentState = currentState.transition(c);
    }
}

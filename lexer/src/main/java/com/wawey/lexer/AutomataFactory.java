package com.wawey.lexer;

public class AutomataFactory {
    public Automata automataFor(final String text) {
        AutomataState current = AutomataStateImpl.acceptanceState();
        for (int i = text.length() - 1; i >= 0; i--) {
            final AutomataState currentCurrent = current;
            final int currentI = i;
            current = AutomataStateImpl.intermediateState(
                    new Transition(
                            c -> c == text.charAt(currentI),
                            () -> currentCurrent
                    )
            );
        }
        return new AutomataImpl(current);
    }
}

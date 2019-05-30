package com.wawey.lexer;

import java.util.function.Supplier;

/**
 * @author Tomas Perez Molina
 */
public class Transition {
    private final CharAcceptor acceptor;
    private final Supplier<AutomataState> stateSupplier;

    public Transition(CharAcceptor acceptor, Supplier<AutomataState> stateSupplier) {
        this.acceptor = acceptor;
        this.stateSupplier = stateSupplier;
    }

    boolean consumes(char c) {
        return acceptor.accepts(c);
    }

    AutomataState nextState() {
        return stateSupplier.get();
    }

    public static Transition selfTransition(CharAcceptor acceptor) {
        return new Transition(acceptor, () -> null);
    }
}

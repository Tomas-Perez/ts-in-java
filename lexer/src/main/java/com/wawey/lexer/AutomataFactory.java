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

    public Automata infiniteRegexAutomata(final String singleCharRegex) {
        CharAcceptor acceptor = new RegexAcceptor(singleCharRegex);
        AutomataState accepting = AutomataStateImpl.acceptanceState(
                Transition.selfTransition(acceptor)
        );
        AutomataState initial = AutomataStateImpl.intermediateState(
                new Transition(acceptor, () -> accepting)
        );
        return new AutomataImpl(initial);
    }

    public Automata delimitedWordAutomata(final char delimiter) {
        final CharAcceptor delimiterAcceptor = (c) -> c == delimiter;
        final AutomataState finalState = AutomataStateImpl.acceptanceState();
        final AutomataState withinDelimiters = AutomataStateImpl.intermediateState(
                Transition.selfTransition((c) -> c != delimiter),
                new Transition(delimiterAcceptor, () -> finalState)
        );
        final AutomataState initialState = AutomataStateImpl.intermediateState(
                new Transition(delimiterAcceptor, () -> withinDelimiters)
        );
        return new AutomataImpl(initialState);
    }

    public Automata delimitedWordAutomata2(final char delimiter) {
        return new LinkedAutomata.Builder()
                .andThen(singleCharAutomata(delimiter))
                .maybeThen(
                        new AutomataImpl(
                                AutomataStateImpl.acceptanceState(
                                        Transition.selfTransition((c) -> c != delimiter)
                                )
                        )
                )
                .andThen(singleCharAutomata(delimiter))
                .build();
    }

    public Automata singleCharAutomata(final char c) {
        return automataFor("" + c);
    }

}

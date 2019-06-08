package com.wawey.lexer;

import java.util.List;

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

    public Automata delimitedWordAutomata(final char delimiter, final List<Character> except) {
        return new LinkedAutomata.Builder()
                .andThen(singleCharAutomata(delimiter))
                .maybeThen(
                        new AutomataImpl(
                                AutomataStateImpl.acceptanceState(
                                        Transition.selfTransition((c) -> c != delimiter && !except.contains(c))
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

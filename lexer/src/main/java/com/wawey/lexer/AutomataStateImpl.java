package com.wawey.lexer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Tomas Perez Molina
 */
public class AutomataStateImpl implements AutomataState {
    private final boolean acceptable;
    private final List<Transition> transitions;

    private AutomataStateImpl(boolean acceptable, List<Transition> transitions) {
        this.acceptable = acceptable;
        this.transitions = transitions;
    }

    private AutomataStateImpl(boolean acceptable) {
        this.acceptable = acceptable;
        this.transitions = Collections.emptyList();
    }

    @Override
    public boolean isAcceptable() {
        return acceptable;
    }

    @Override
    public AutomataState transition(char c) {
        return transitions.stream()
                .filter(t -> t.consumes(c))
                .findFirst()
                .map(Transition::nextState)
                .orElseThrow(NoTransitionException::new);
    }

    public static AutomataStateImpl acceptanceState(Transition... transitions) {
        return new AutomataStateImpl(true, Arrays.asList(transitions));
    }

    public static AutomataStateImpl acceptanceState() {
        return new AutomataStateImpl(true);
    }

    public static AutomataStateImpl intermediateState(Transition... transitions) {
        return new AutomataStateImpl(false, Arrays.asList(transitions));
    }
}

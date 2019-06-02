package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class TransitionState implements ParserAutomataState {
    private final List<Transition> transitions;
    private final boolean acceptable;

    public TransitionState(boolean acceptable, List<Transition> transitions) {
        this.transitions = transitions;
        this.acceptable = acceptable;
    }

    public TransitionState(Transition... transitions) {
        this(false, Arrays.asList(transitions));
    }

    public TransitionState(Transition transition) {
        this(false, Collections.singletonList(transition));
    }

    public TransitionState(boolean acceptable, Transition... transitions) {
        this(acceptable, Arrays.asList(transitions));
    }

    public TransitionState(boolean acceptable, Transition transition) {
        this(acceptable, Collections.singletonList(transition));
    }

    @Override
    public boolean accepts(Token token) {
        return transitions.stream().anyMatch(t -> t.consumes(token));
    }

    @Override
    public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
        return transitions.stream()
                .filter(t -> t.consumes(token))
                .findFirst()
                .map(t -> t.nextState(token, stack))
                .orElseThrow(NoTransitionException::new);
    }

    @Override
    public boolean isAcceptable() {
        return acceptable;
    }
}

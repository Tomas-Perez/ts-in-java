package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.function.Supplier;

/**
 * @author Tomas Perez Molina
 */
public class TransitionToAutomata implements Transition {
    private final ParserAutomata automata;
    private final Supplier<ParserAutomataState> onFinishTransition;

    public TransitionToAutomata(ParserAutomata automata, Supplier<ParserAutomataState> onFinishTransition) {
        this.automata = automata;
        this.onFinishTransition = onFinishTransition;
    }

    @Override
    public boolean consumes(Token token) {
        return automata.accepts(token);
    }

    @Override
    public StateChange nextState(Token token, ImmutableStack<ASTNode> stack) {
        ParserAutomataState next = new InnerAutomataState(automata, onFinishTransition);
        return next.transition(token, stack);
    }
}

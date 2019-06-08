package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.function.Function;
import java.util.function.Supplier;

public class InnerAutomataState implements ParserAutomataState {
    private final ParserAutomata automata;
    private final Supplier<ParserAutomataState> next;
    private final Function<ImmutableStack<ASTNode>, ImmutableStack<ASTNode>> onFinish;

    public InnerAutomataState(ParserAutomata automata, Supplier<ParserAutomataState> next) {
        this.automata = automata;
        this.next = next;
        this.onFinish = Function.identity();
    }

    public InnerAutomataState(ParserAutomata automata, Supplier<ParserAutomataState> next, Function<ImmutableStack<ASTNode>, ImmutableStack<ASTNode>> onFinish) {
        this.automata = automata;
        this.next = next;
        this.onFinish = onFinish;
    }

    @Override
    public boolean accepts(Token token) {
        return automata.accepts(token);
    }

    @Override
    public StateChange transition(Token token, ImmutableStack<ASTNode> stack) {
        automata.consume(token);
        if (automata.acceptable()) {
            ImmutableStack<ASTNode> newStack = stack.push(automata.getResult());
            ImmutableStack<ASTNode> afterFinish = onFinish.apply(newStack);
            return new StateChangeImpl(
                    new DualState(this, stack, next.get()),
                    afterFinish
            );
        }
        return new StateChangeImpl(this, stack);
    }

    @Override
    public boolean isAcceptable() {
        return false;
    }

    private static class DualState implements ParserAutomataState {
        final ParserAutomataState first;
        final ImmutableStack<ASTNode> beforeFinish;
        final ParserAutomataState second;

        public DualState(ParserAutomataState first, ImmutableStack<ASTNode> beforeFinish, ParserAutomataState second) {
            this.first = first;
            this.beforeFinish = beforeFinish;
            this.second = second;
        }

        @Override
        public StateChange transition(Token token, ImmutableStack<ASTNode> stack) {
            try {
                return first.transition(token, beforeFinish);
            } catch (NoTransitionException exc) {
                return second.transition(token, stack);
            }
        }

        @Override
        public boolean isAcceptable() {
            return first.isAcceptable() || second.isAcceptable();
        }

        @Override
        public boolean accepts(Token token) {
            return first.accepts(token) || second.accepts(token);
        }
    }
}

package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InnerAutomataState implements ParserAutomataState {
    private final ParserAutomata automata;
    private final Supplier<ParserAutomataState> next;
    private final Consumer<Stack<ASTNode>> onFinish;

    public InnerAutomataState(ParserAutomata automata, Supplier<ParserAutomataState> next) {
        this.automata = automata;
        this.next = next;
        this.onFinish = (s) -> {};
    }

    public InnerAutomataState(ParserAutomata automata, Supplier<ParserAutomataState> next, Consumer<Stack<ASTNode>> onFinish) {
        this.automata = automata;
        this.next = next;
        this.onFinish = onFinish;
    }

    @Override
    public boolean accepts(Token token) {
        return automata.accepts(token);
    }

    @Override
    public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
        automata.consume(token);
        if (automata.acceptable()) {
            stack.push(automata.getResult());
            onFinish.accept(stack);
            return new DualState(this, next.get());
        }
        return this;
    }

    @Override
    public boolean isAcceptable() {
        return false;
    }

    private static class DualState implements ParserAutomataState {
        final ParserAutomataState first;
        final ParserAutomataState second;

        public DualState(ParserAutomataState first, ParserAutomataState orNext) {
            this.first = first;
            this.second = orNext;
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            try {
                return first.transition(token, stack);
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

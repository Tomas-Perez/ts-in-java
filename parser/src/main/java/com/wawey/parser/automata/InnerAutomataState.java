package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
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
            return next.get();
        }
        return this;
    }

    @Override
    public boolean isAcceptable() {
        return false;
    }
}

package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;
import java.util.function.Supplier;

public class InnerAutomataState implements ParserAutomataState {
    private final ParserAutomata automata;
    private final Supplier<ParserAutomataState> next;

    public InnerAutomataState(ParserAutomata automata, Supplier<ParserAutomataState> next) {
        this.automata = automata;
        this.next = next;
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
            return next.get();
        }
        return this;
    }

    @Override
    public boolean isAcceptable() {
        return false;
    }
}

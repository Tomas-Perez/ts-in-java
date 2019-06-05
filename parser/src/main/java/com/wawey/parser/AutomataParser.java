package com.wawey.parser;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.automata.ParserAutomata;

import java.util.List;

public class AutomataParser implements Parser {
    private final ParserAutomata automata;

    public AutomataParser(ParserAutomata automata) {
        this.automata = automata;
    }

    @Override
    public ASTNode parse(List<Token> tokens) {
        tokens.forEach(t -> {
            automata.consume(t);
        });
        return automata.getResult();
    }
}

package com.wawey.parser;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.automata.NoTransitionException;
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
            try {
                automata.consume(t);
            } catch (NoTransitionException exc) {
                if (exc.getToken().getType() == TokenType.EOF) {
                    throw new UnexpectedEndOfFileException(exc.getToken().getLine(), exc.getToken().getStartColumn());
                } else {
                    throw new UnexpectedTokenException(exc.getToken());
                }
            }
        });
        return automata.getResult();
    }
}

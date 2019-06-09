package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

public interface ParserAutomata {
    void consume(Token token);
    ASTNode getResult();
    boolean acceptable();
    boolean accepts(Token token);
}

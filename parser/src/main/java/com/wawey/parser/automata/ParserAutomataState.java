package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

public interface ParserAutomataState {
    StateChange transition(Token token, ImmutableStack<ASTNode> stack);
    boolean isAcceptable();
    boolean accepts(Token token);
}

package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

public interface Transition {
    boolean consumes(Token token);

    StateChange nextState(Token token, ImmutableStack<ASTNode> stack);
}

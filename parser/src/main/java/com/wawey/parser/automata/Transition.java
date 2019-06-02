package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.ast.ASTNode;

import java.util.Stack;

public interface Transition {
    boolean consumes(Token token);

    StateChange nextState(Token token, Stack<ASTNode> stack);
}
